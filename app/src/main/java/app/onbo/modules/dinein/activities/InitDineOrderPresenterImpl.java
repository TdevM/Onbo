package app.onbo.modules.dinein.activities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import app.onbo.api.APIService;
import app.onbo.api.cart.CartItem;
import app.onbo.api.models.response.v2.t_orders.TOrder;
import app.onbo.base.BasePresenter;
import app.onbo.modules.dinein.DineInPresenterContract;
import app.onbo.modules.dinein.DineInViewContract;
import app.onbo.utils.PreferenceUtils;
import app.onbo.utils.CartHelper;

/**
 * Created by Tridev on 29-12-2017.
 */

public class InitDineOrderPresenterImpl extends BasePresenter implements DineInPresenterContract.PlaceTempOrderPresenter {

    public static final String TAG = InitDineOrderPresenterImpl.class.getSimpleName();

    private APIService apiService;
    private PreferenceUtils preferenceUtils;
    private CompositeDisposable compositeDisposable;
    private DineInViewContract.PlaceTempOrderView placeTempOrderView;
    private CartHelper cart;

    @Inject
    public InitDineOrderPresenterImpl(APIService apiService, PreferenceUtils preferenceUtils, CartHelper cartHelper) {
        this.apiService = apiService;
        this.cart = cartHelper;
        this.preferenceUtils = preferenceUtils;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(DineInViewContract.PlaceTempOrderView view) {
        this.placeTempOrderView = view;
    }


    public void clearCart() {
        cart.clearCart();
    }

    public JSONArray convertCartTOJSON() {
        JSONArray root = new JSONArray();
        Single<List<CartItem>> cartItems = cart.getCartItems();
        cartItems.subscribe(new SingleObserver<List<CartItem>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(List<CartItem> cartItems) {
                for (int i = 0; i < cartItems.size(); i++) {
                    JSONObject rootObject = new JSONObject();
                    JSONArray variants = new JSONArray();
                    JSONArray addOns = new JSONArray();
                    JSONArray vExtra = new JSONArray();
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
                        rootObject.put("variants_extras", vExtra);
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
            }

            @Override
            public void onError(Throwable e) {

            }
        });

        return root;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }

    @Override
    public void checkCurrentOrderDetails() {
        Log.d(TAG, "Checking order...");
        Map<String, String> map = new HashMap<>();
        //map.put("restaurant_id", preferenceUtils.getScannedRestaurantId());

        Observable<Response<TOrder>> observable = apiService.fetchMyRunningOrder("Bearer " + preferenceUtils.getAuthLoginToken(), map);
        subscribe(observable, new Observer<Response<TOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                placeTempOrderView.showProgressUI();
            }

            @Override
            public void onNext(Response<TOrder> arrayListResponse) {
                Log.d(TAG, "onNext RAN");
                if (arrayListResponse.code() == 200) {
                    if (arrayListResponse.body() != null) {
                        if (arrayListResponse.body().getClosed()) {
                            placeTempOrderView.showOrderAlreadyClosed();
                        } else {
                            placeTempOrderView.showGetMessage(arrayListResponse);
                        }
                    }
                    Log.d(TAG, "Add items to Order RAN");
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
                placeTempOrderView.hideProgressUI();
            }
        });
    }

}
