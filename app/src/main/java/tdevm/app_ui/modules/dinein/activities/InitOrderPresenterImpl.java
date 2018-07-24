package tdevm.app_ui.modules.dinein.activities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.cart.CartItem;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.dinein.DineInPresenterContract;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.utils.AuthUtils;
import tdevm.app_ui.utils.CartHelper;

/**
 * Created by Tridev on 29-12-2017.
 */

public class InitOrderPresenterImpl extends BasePresenter implements DineInPresenterContract.PlaceTempOrderPresenter {

    public static final String TAG = InitOrderPresenterImpl.class.getSimpleName();

    private APIService apiService;
    private AuthUtils authUtils;
    private CompositeDisposable compositeDisposable;
    private DineInViewContract.PlaceTempOrderView placeTempOrderView;
    private CartHelper cart;

    @Inject
    public InitOrderPresenterImpl(APIService apiService, AuthUtils authUtils, CartHelper cartHelper) {
        this.apiService = apiService;
        this.cart = cartHelper;
        this.authUtils = authUtils;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(DineInViewContract.PlaceTempOrderView view) {
        this.placeTempOrderView = view;
    }


    @Override
    public void checkCurrentOrderDetails() {
        Log.d(TAG, "Checking order...");
        Map<String, String> map = new HashMap<>();
        map.put("restaurant_uuid", authUtils.getScannedRestaurantUuid());
        Observable<Response<ArrayList<TempOrder>>> observable = apiService.fetchMyRunningOrder(authUtils.getAuthLoginToken(), map);
        subscribe(observable, new Observer<Response<ArrayList<TempOrder>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<ArrayList<TempOrder>> arrayListResponse) {
                Log.d(TAG, "onNext RAN");
                if (arrayListResponse.isSuccessful()) {
                    if (arrayListResponse.code() == 200) {
                        placeTempOrderView.showGetMessage(arrayListResponse);
                        Log.d(TAG, "Add items to Order RAN");
                    }
                } else if (!arrayListResponse.isSuccessful() && arrayListResponse.code() == 404) {
                    //Create new Order
                    Log.d(TAG, "Create Order RAN");
                    placeTempOrderView.showGetGuestMessage();
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
    public void addItemsToOrder(String userMessage, ArrayList<TempOrder> arrayList) {
        //  JSONArray message = updateUserMessage(userMessage,arrayList.get(0).getKot_messages());
//        RestaurantOrder order = new RestaurantOrder(arrayList.get(0).getOrder_id(),message.toString(),convertCartTOJSON().toString());
//        Observable<Response<Object>> observable = apiService.addItemsToTempOrder(authUtils.getAuthLoginToken(),order);
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

    public void clearCart() {
        cart.clearCart();
    }


    @Override
    public void createNewOrder(int guest, String message) {
//        JSONArray array = createNewUserMessage(message);
//        RestaurantOrder order = new RestaurantOrder(authUtils.getScannedRestaurantUuid(),authUtils.getScannedRestaurantTableShortId(),array.toString(),convertCartTOJSON().toString(),guest);
//        Observable<Response<Object>> observable = apiService.createNewTempOrder(authUtils.getAuthLoginToken(),order);
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

    public JSONArray convertCartTOJSON() {
        List<CartItem> cartItems = cart.getCartItems();
        JSONArray root = new JSONArray();
        for (int i = 0; i < cartItems.size(); i++) {
            JSONObject rootObject = new JSONObject();
            JSONArray variants = new JSONArray();
            JSONArray addOns = new JSONArray();
            for (int j = 0; j < 2; j++) {
                try {
                    rootObject.put("item_id", cartItems.get(i).getMenuItem().getItemId());
                    rootObject.put("item_qty", cartItems.get(i).getQuantity());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            root.put(rootObject);
            for (int x = 0; x < cartItems.get(i).getMenuItem().getMenuVariantOptions().size(); x++) {

                JSONObject variantObjects = new JSONObject();
                for (int j = 0; j < 2; j++) {
                    try {
                        variantObjects.put("variant_option_id", cartItems.get(i).getMenuItem().getMenuVariantOptions().get(x).getOptionId());
                        variantObjects.put("variant_id", cartItems.get(i).getMenuItem().getMenuVariantOptions().get(x).getVariantId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                variants.put(variantObjects);
            }
            try {
                rootObject.put("order_variants", variants);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int x = 0; x < cartItems.get(i).getMenuItem().getMenuAddOns().size(); x++) {
                JSONObject addOnObjects = new JSONObject();
                for (int j = 0; j < 2; j++) {
                    try {
                        addOnObjects.put("menu_add_on_id", cartItems.get(i).getMenuItem().getMenuAddOns().get(x).getAddOnId());
                        addOnObjects.put("group_id", cartItems.get(i).getMenuItem().getMenuAddOns().get(x).getGroupId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                addOns.put(addOnObjects);
            }
            try {
                rootObject.put("order_add_ons", addOns);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return root;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }

}
