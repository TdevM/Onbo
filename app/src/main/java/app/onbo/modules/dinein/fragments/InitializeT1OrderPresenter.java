package app.onbo.modules.dinein.fragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
import app.onbo.api.models.request.RestaurantOrder;
import app.onbo.api.models.response.v2.t_orders.TOrder;
import app.onbo.base.BasePresenter;
import app.onbo.modules.dinein.DineInPresenterContract;
import app.onbo.modules.dinein.DineInViewContract;
import app.onbo.utils.CartHelper;
import app.onbo.utils.PreferenceUtils;

public class InitializeT1OrderPresenter extends BasePresenter implements DineInPresenterContract.InitializeT1OrderPresenterContract {


    public static final String TAG = InitializeT1OrderPresenter.class.getSimpleName();


    private APIService apiService;
    private CartHelper cartHelper;

    private PreferenceUtils preferenceUtils;

    private CompositeDisposable compositeDisposable;
    private DineInViewContract.InitializeT1OrderView initializeT1OrderView;

    @Inject
    public InitializeT1OrderPresenter(APIService apiService, CartHelper cartHelper, PreferenceUtils preferenceUtils) {
        this.apiService = apiService;
        this.cartHelper = cartHelper;
        this.preferenceUtils = preferenceUtils;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(DineInViewContract.InitializeT1OrderView view) {
        this.initializeT1OrderView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }


    @Override
    public void addItemsToOrder(String userMessage, TOrder tOrder) {
        RestaurantOrder order = new RestaurantOrder(preferenceUtils.getScannedRestaurantId(), tOrder.getOrderId(), userMessage, convertCartTOJSON().toString());
        Observable<Response<TOrder>> observable = apiService.addItemsToTempOrder("Bearer " + preferenceUtils.getAuthLoginToken(), order);
        subscribe(observable, new Observer<Response<TOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                initializeT1OrderView.showProgressUI();
            }

            @Override
            public void onNext(Response<TOrder> objectResponse) {
                if (objectResponse.isSuccessful()) {
                    if (objectResponse.code() == 200) {
                        initializeT1OrderView.onOrderItemsAdded(objectResponse.body());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                initializeT1OrderView.hideProgressUI();
            }
        });
    }

    public void clearCart() {
        cartHelper.clearCart();
    }


    @Override
    public void createNewOrder(int guest, String message) {
        RestaurantOrder order = new RestaurantOrder(preferenceUtils.getScannedRestaurantId(), preferenceUtils.getFetchedRestaurantTableId(), message, convertCartTOJSON().toString(), guest);
        Observable<Response<TOrder>> observable = apiService.createNewTempOrder("Bearer " + preferenceUtils.getAuthLoginToken(), order);
        subscribe(observable, new Observer<Response<TOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                initializeT1OrderView.showProgressUI();
            }

            @Override
            public void onNext(Response<TOrder> objectResponse) {
                if (objectResponse.isSuccessful()) {
                    initializeT1OrderView.onNewOrderCreated(objectResponse.body());
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                initializeT1OrderView.hideProgressUI();
            }
        });
    }


    public JSONArray convertCartTOJSON() {
        JSONArray root = new JSONArray();
        Single<List<CartItem>> cartItems = cartHelper.getCartItems();
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
}
