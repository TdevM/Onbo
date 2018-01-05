package tdevm.app_ui.modules.dinein.activities;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.response.TempOrder;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.dinein.DineInPresenterContract;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.utils.AuthUtils;

/**
 * Created by Tridev on 29-12-2017.
 */

public class PlaceTempOrderPresenterImpl extends BasePresenter implements DineInPresenterContract.PlaceTempOrderPresenter {

    public static final String TAG = PlaceTempOrderPresenterImpl.class.getSimpleName();

    private APIService apiService;
    private AuthUtils authUtils;
    private CompositeDisposable compositeDisposable;
    private DineInViewContract.PlaceTempOrderView placeTempOrderView;

    @Inject
    public PlaceTempOrderPresenterImpl(APIService apiService, AuthUtils authUtils) {
        this.apiService = apiService;
        this.authUtils = authUtils;
        compositeDisposable  = new CompositeDisposable();
    }

    @Override
    public void attachView(DineInViewContract.PlaceTempOrderView view) {
        placeTempOrderView = view;
    }

    @Override
    public void detachView() {
      compositeDisposable.clear();
      compositeDisposable.dispose();
    }

    @Override
    public void checkCurrentOrderDetails(Map<String, String> map) {
        Observable<Response<ArrayList<TempOrder>>> observable = apiService.fetchMyRunningOrder(authUtils.getAuthLoginToken(),map);
        subscribe(observable, new Observer<Response<ArrayList<TempOrder>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<ArrayList<TempOrder>> arrayListResponse) {
                if (arrayListResponse.isSuccessful()) {
                    if (arrayListResponse.code() == 200) {
                        placeTempOrderView.onOrderDetailsFetched("Order running");
                        if (arrayListResponse.body() != null) {
                            addItemsToOrder(arrayListResponse.body());
                        }
                    }
                } else if (!arrayListResponse.isSuccessful() && arrayListResponse.code() == 404) {
                    createNewOrder();
                    placeTempOrderView.onOrderDetailsFetched("No order running");
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void addItemsToOrder(ArrayList<TempOrder> arrayList) {
//        RestaurantOrder order = new RestaurantOrder(arrayList.get(1).getOrder_id(),convertTOJSON().toString());
//        Observable<Response<Object>> observable = apiService.addItemsToOrder(authUtils.getAuthLoginToken(),order);
//         subscribe(observable, new Observer<Response<Object>>() {
//             @Override
//             public void onSubscribe(Disposable d) {
//                compositeDisposable.add(d);
//             }
//             @Override
//             public void onNext(Response<Object> objectResponse) {
//                if(objectResponse.isSuccessful()){
//                    if(objectResponse.code()==200){
//                        placeTempOrderView.onOrderItemsAdded();
//                    }
//                }
//             }
//
//             @Override
//             public void onError(Throwable e) {
//
//             }
//
//             @Override
//             public void onComplete() {
//
//             }
//         });
    }


    @Override
    public void createNewOrder() {
//        RestaurantOrder order = new RestaurantOrder(authUtils.getScannedRestaurantUuid(),authUtils.getScannedRestaurantTableShortId(),"masala",convertTOJSON().toString(),4);
//        Observable<Response<Object>> observable = apiService.createNewOrder(authUtils.getAuthLoginToken(),order);
//        subscribe(observable, new Observer<Response<Object>>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                compositeDisposable.add(d);
//            }
//
//            @Override
//            public void onNext(Response<Object> objectResponse) {
//               if(objectResponse.isSuccessful()){
//                   placeTempOrderView.onNewOrderCreated();
//               }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
    }

//    public JSONArray convertTOJSON(){
//        List<CartItem> cartItems = appDatabase.cartItemDao().getCartItems();
//        JSONArray jArray = new JSONArray();
//        for (CartItem item: cartItems) {
//            JSONObject jsonObject = new JSONObject();
//            for (int j = 0; j < 2; j++) {
//                try {
//                    jsonObject.put("dish_id", item.getDishesOfCuisine().getDish_id());
//                    jsonObject.put("dish_quantity", item.getQuantity());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            jArray.put(jsonObject);
//        }
//        return jArray;
//    }
}
