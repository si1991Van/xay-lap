package com.viettel.construction.server.service;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.viettel.construction.server.constant.Constant;
import com.viettel.construction.server.data.model.RequestInfor;
import com.viettel.construction.server.service.volley.VolleySingleton;
import com.viettel.construction.server.util.RequestHandler;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class ApiClient {
    private final String TAG = "VTApiClient";
    private static ApiClient instance;

    private ApiClient() {

    }

    public static ApiClient getInstance() {
        return instance == null ? new ApiClient() : instance;
    }

    JsonDeserializer<Date> deserializerDate = new JsonDeserializer<Date>() {
        @Override
        public Date deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            try {
                return json == null ? null : format.parse(json.getAsString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    };

    public <T> void postDate(String url, final Class<T> clazz, final Map<String, String> headers,
                             final String requestBody, final IOnRequestListener requestListener) {
        // Todo delete when release
        final RequestInfor requestInfor = new RequestInfor(url, headers, requestBody);
        Log.d("Body", "---------------------------------------");
        if (headers != null)
            Log.d("headers", headers.toString());
        if (requestBody != null)
            Log.d("requestBody", requestBody);
        Log.d("url", url);
        StringRequest request =
                new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    GsonBuilder builder = new GsonBuilder();
                                    builder.registerTypeAdapter(Date.class, deserializerDate);
                                    Gson gson = builder.create();
                                    Log.d("response", response);
                                    T result = gson.fromJson(response, clazz);
                                    if (requestListener != null)
                                        requestListener.onResponse(result);
                                } catch (JsonSyntaxException e) {
                                    e.printStackTrace();
                                    Log.d("JsonSyntaxException", e.getMessage());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (requestListener != null)
                            requestListener.onError(parseError(error));
                        Log.d("onErrorResponse", error.toString());
                    }
                }) {
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            Log.d("UnsupportedEncoding", "Unsupported Encoding");
                            return null;
                        }
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return headers != null ? headers : super.getHeaders();
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        // Todo delete when release
                        try {
                            requestInfor.setResponse(new String(response.data));
                            RequestHandler.getInstance().addRequest(requestInfor);
                        } catch (OutOfMemoryError e) {
                            Log.d(TAG, "OutOfMemoryError");
                        }

                        return parseResponse(response);
                    }
                };

//        request.setRetryPolicy(new DefaultRetryPolicy(Constant.REQUEST_TIME_OUT,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.setRetryPolicy(new DefaultRetryPolicy(Constant.REQUEST_TIME_OUT,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance().addToRequestQueue(request);
    }


    public <T> void post(String url, final Class<T> clazz, final Map<String, String> headers,
                         final String requestBody, final IOnRequestListener requestListener) {
        // Todo delete when release
        final RequestInfor requestInfor = new RequestInfor(url, headers, requestBody);
        Log.d("Body", "---------------------------------------");
        if (headers != null)
            Log.d(TAG, "headers : " + headers.toString());
        if (requestBody != null)
            Log.d(TAG, "requestBody : " + requestBody);
        Log.d("url", url);
        StringRequest request =
                new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Gson gson = new Gson();
                                    Log.d(TAG, "response : " + response);
                                    T result = gson.fromJson(response, clazz);
                                    if (requestListener != null)
                                        requestListener.onResponse(result);
                                } catch (JsonSyntaxException e) {
                                    e.printStackTrace();
                                    Log.d(TAG, "JsonSyntaxException : " + e.getMessage());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (requestListener != null)
                            requestListener.onError(parseError(error));
                        Log.d(TAG, "onErrorResponse : " + error.getMessage());
                    }
                }) {
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            Log.d("UnsupportedEncoding", "Unsupported Encoding");
                            return null;
                        }
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return headers != null ? headers : super.getHeaders();
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        // Todo delete when release
                        try {
                            requestInfor.setResponse(new String(response.data));
                            RequestHandler.getInstance().addRequest(requestInfor);
                        } catch (OutOfMemoryError e) {
                            Log.d(TAG, "OutOfMemoryError");
                        }

                        return parseResponse(response);
                    }
                };

//        request.setRetryPolicy(new DefaultRetryPolicy(Constant.REQUEST_TIME_OUT,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.setRetryPolicy(new DefaultRetryPolicy(Constant.REQUEST_TIME_OUT,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //  Log.d(TAG,"StringRequest : " + request.toString());
        VolleySingleton.getInstance().addToRequestQueue(request);
    }


    public <T> void post1(String url, final Class<T> clazz, final Map<String, String> headers,
                          final String requestBody, final IServerResultListener<T> requestListener) {
        // Todo delete when release
        final RequestInfor requestInfor = new RequestInfor(url, headers, requestBody);
        Log.d("Body", "---------------------------------------");
        if (headers != null)
            Log.d("headers", headers.toString());
        if (requestBody != null)
            Log.d("requestBody", requestBody);
        Log.d("url", url);
        StringRequest request =
                new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Gson gson = new Gson();
                                    Log.d("response", response);
                                    T result = gson.fromJson(response, clazz);
                                    if (requestListener != null)
                                        requestListener.onResponse(result);
                                } catch (JsonSyntaxException e) {
                                    e.printStackTrace();
                                    Log.d("JsonSyntaxException", e.getMessage());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (requestListener != null)
                            requestListener.onError(parseError(error));
                        Log.d("onErrorResponse", error.toString());
                    }
                }) {
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            Log.d("UnsupportedEncoding", "Unsupported Encoding");
                            return null;
                        }
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return headers != null ? headers : super.getHeaders();
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        // Todo delete when release
                        try {
                            requestInfor.setResponse(new String(response.data));
                            RequestHandler.getInstance().addRequest(requestInfor);
                        }catch (OutOfMemoryError e){
                            Log.d(TAG, "OutOfMemoryError");
                        }

                        return parseResponse(response);
                    }
                };

        request.setRetryPolicy(new DefaultRetryPolicy(Constant.REQUEST_TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

//        request.setRetryPolicy(new DefaultRetryPolicy(Constant.REQUEST_TIME_OUT,
//                0,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance().addToRequestQueue(request);
    }

    public <T> void get(String url, final Class<T> clazz, final Map<String, String> headers, final IOnRequestListener requestListener) {
        // Todo delete when release
        RequestInfor requestInfor = new RequestInfor(url, headers, null);
        final StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(Constant.REQUEST_TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance().addToRequestQueue(request);
    }


    public Response<String> parseResponse(NetworkResponse response) {
        int statusCode = response.statusCode;
        if (Constant.STATUS_CODE_SUCCESS == statusCode) {
            String parsed = null;
            try {
                String charset = HttpHeaderParser.parseCharset(response.headers);
                parsed = new String(response.data, charset);
            } catch (OutOfMemoryError e) {
                Log.d(TAG, "OutOfMemoryError");
            } catch (UnsupportedEncodingException e) {
                try {
                    parsed = new String(response.data);
                } catch (OutOfMemoryError e1) {
                    Log.d(TAG, "OutOfMemoryError");
                }

            }
            return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
        }
        VolleyError error = new VolleyError(response);
        return Response.error(error);
    }

    public int parseError(VolleyError error) {
        if (error == null || error.networkResponse == null)
            return -1;
        NetworkResponse response = error.networkResponse;
        return response.statusCode;
    }
}
