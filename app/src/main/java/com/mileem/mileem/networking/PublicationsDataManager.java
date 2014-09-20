package com.mileem.mileem.networking;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.mileem.mileem.models.Pagination;
import com.mileem.mileem.models.PublicationDetails;
import com.mileem.mileem.models.PublicationFilter;
import com.mileem.mileem.models.PublicationOrder;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by ramirodiaz on 13/09/14.
 */
public class PublicationsDataManager {
    public static abstract class PublicationsCallbackHandler extends CallbackHandler {
        public abstract void onComplete(ArrayList collection);
    }
    private ArrayList <Integer> publicationsIds = null;
    private PublicationFilter filter = null;
    private Pagination pagination = null;
    private PublicationOrder order = null;

    private HashMap<PublicationOrder.OrderBy, String > orderByMap = new HashMap<PublicationOrder.OrderBy, String>(){{
        put(PublicationOrder.OrderBy.PUBLISH_DATE,"create_at");
        put(PublicationOrder.OrderBy.PRICE,"price");
        put(PublicationOrder.OrderBy.PRIORITY,"publication_type_id");
    }};

    private HashMap<PublicationOrder.Order, String > orderMap = new HashMap<PublicationOrder.Order, String>(){{
        put(PublicationOrder.Order.ASC,"asc");
        put(PublicationOrder.Order.DESC,"desc");
    }};

    private Boolean checkMinimumFilter(PublicationFilter filter) {
        Boolean existNeighborhoods = filter != null && filter.getNeighborhoods() != null;
        Boolean existEnvironments = filter != null && filter.getEnvironments() != null;
        Boolean existPropertyTypes = filter != null && filter.getPropertyTypes() != null;
        Boolean existOperationTypes = filter != null && filter.getOperationTypes() != null;
        return existNeighborhoods && existEnvironments && existPropertyTypes && existOperationTypes;
    }

    private String orderToString (PublicationOrder order) {
        String orderByString = this.orderByMap.get(order.getOrderBy());
        String orderString = this.orderMap.get(order.getOrder());
        StringBuilder value = new StringBuilder();
        value.append(orderByString).append(",").append(orderString);
        return value.toString();
    };

    private String toCommaDelimitedValue (int[] array) {
        if (array.length > 0) {
            StringBuilder value = new StringBuilder();
            for (int n : array) {
                value.append(n).append(",");
            }
            value.deleteCharAt(value.length() - 1);
            return value.toString();
        } else {
            return "";
        }
    }

    private RequestParams prepareParams(Pagination pagination) {
        RequestParams params = new RequestParams();
        params.put("neighborhoods", toCommaDelimitedValue(this.filter.getNeighborhoods()));
        params.put("propertyTypes", toCommaDelimitedValue(this.filter.getPropertyTypes()));
        params.put("operationTypes", toCommaDelimitedValue(this.filter.getOperationTypes()));
        params.put("environments", toCommaDelimitedValue(this.filter.getEnvironments()));
        params.put("order", this.orderToString(this.order));
        if (this.filter.getMinPrice() != 0) params.put("minPrice", this.filter.getMinPrice());
        if (this.filter.getMaxPrice() != 0) params.put("maxPrice", this.filter.getMaxPrice());
        if (this.filter.getMinSize() != 0) params.put("minSize", this.filter.getMinSize());
        if (this.filter.getMinCoveredSize() != 0) params.put("minCoveredSize", this.filter.getMinCoveredSize());
        //TODO implementar del lado del server para recibir un id Number tal como sugirio ale
        //params.put("minPublishDate", this.filter.getMinPublishDate());
        if (pagination != null) {
            if (pagination.getAmount() != 0) params.put("amount", pagination.getAmount());
            params.put("offset", pagination.getOffset());
        }
        return params;
    }

    private void addNewIdsInPublicationsIds(ArrayList collection) {
     Iterator<PublicationDetails> iterator = collection.iterator();
     while (iterator.hasNext()) {
         PublicationDetails publication = iterator.next();
         int id = publication.getId();
         publicationsIds.add(id);
     }
    }

    private ArrayList<PublicationDetails> parseResponse(JSONObject response) {
        ArrayList<PublicationDetails> newPageCollection = null;
        try{
            JSONObject payload = response.getJSONObject("payload");
            Object publications = payload.get("publications");
            Boolean lastPageReached = payload.getBoolean("lastPageReached");
            Type collectionType = new TypeToken<ArrayList<PublicationDetails>>() {
            }.getType();
            Gson gson = new Gson();
            newPageCollection = gson.fromJson(publications.toString(), collectionType);
            newPageCollection =  this.removeDuplicatedIds(newPageCollection);
            this.addNewIdsInPublicationsIds(newPageCollection);
            this.pagination.setLastPageReached(lastPageReached);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return newPageCollection;
    }

    private ArrayList<PublicationDetails> removeDuplicatedIds(ArrayList <PublicationDetails> collection) {
        ArrayList <PublicationDetails> copyCollection = (ArrayList <PublicationDetails>) collection.clone();
        Iterator<PublicationDetails> iterator = collection.iterator();
        while (iterator.hasNext()) {
            PublicationDetails publication = iterator.next();
            int id = publication.getId();
            if (publicationsIds.contains(id)) {
                copyCollection.remove(publication);
            }
        }
        return copyCollection;
    }

    public void getPublicationsList(PublicationFilter filter, final Pagination pagination, PublicationOrder order, final PublicationsCallbackHandler callbackHandler) throws JSONException {
        if (!this.checkMinimumFilter(filter)) {
            callbackHandler.onFailure(new Error("'filter' must have, at least, 'Neighborhoods', 'Environments', 'PropertyTypes' and 'OperationType'"));
            return;
        }
        this.publicationsIds = new ArrayList<Integer>();
        this.filter = filter;
        this.order = (order == null) ?new PublicationOrder(PublicationOrder.OrderBy.PRIORITY, PublicationOrder.Order.ASC) : order;
        this.pagination = pagination;

        RequestParams params = this.prepareParams(this.pagination);

        AsyncRestHttpClient.get("property-search", params, new MileenJsonResponseHandler(callbackHandler) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                ArrayList<PublicationDetails> publications = PublicationsDataManager.this.parseResponse(response);
                callbackHandler.onComplete(publications);
            }
        });
    }

    public void getNextPage(final PublicationsCallbackHandler callbackHandler) throws JSONException {
        if (pagination.getLastPageReached()) {
            //callbackHandler.onFailure(new Error("Last page reached"));
            callbackHandler.onComplete(new ArrayList());
            return;
        }

        final int nextOffset = pagination.getAmount() * (pagination.getOffset() + 1);
        Pagination tempPagination = new Pagination(pagination.getAmount());
        tempPagination.setOffset(nextOffset);
        RequestParams params = this.prepareParams(tempPagination);

        AsyncRestHttpClient.get("property-search", params, new MileenJsonResponseHandler(callbackHandler) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                ArrayList<PublicationDetails> publications = PublicationsDataManager.this.parseResponse(response);
                PublicationsDataManager.this.pagination.setOffset(nextOffset);
                callbackHandler.onComplete(publications);
            }
        });
    }
}
