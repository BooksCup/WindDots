package com.wd.winddots.net;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface RetrofitService {

    /**
     * 登录
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})//需要添加头
    @POST("cloud-user/login")
    Observable<String> login(@Body RequestBody body);


    /**
     * 检查更新
     *
     * @return
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})//需要添加头
    @GET("/appReleases")
    Observable<String> upgrade(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize, @Query("appName") String appName);


    /**
     * 消息 私聊 列表
     *
     * @param fromId
     * @return
     */
    @GET("conversations/getSingle")
    Observable<String> loadPrivateChatListData(@Query("fromId") String fromId);

    /**
     * 消息 群聊 列表
     *
     * @param viewerId
     * @param page
     * @param pageSize
     * @return
     */
    @GET("work-group-questions/getWorkGroupQuestionGroupList")
    Observable<String> loadGroupChatListData(@Query("viewerId") String viewerId
            , @Query("page") int page
            , @Query("pageSize") int pageSize);

    /**
     * 消息 审批 列表
     *
     * @param userId
     * @param pageNum
     * @param numPerPage
     * @return
     */
    @GET("cloud-app/apply/getApprovalMessage")
    Observable<String> loadExamineApproveListData(@Query("userId") String userId
            , @Query("pageNum") int pageNum
            , @Query("numPerPage") int numPerPage);


    /**
     * 审批流程
     *
     * @param id
     * @param userId
     * @return
     */
    @GET("cloud-app/apply/getById/{id}")
    Observable<String> loadApprovalPocessData(@Path("id") String id
            , @Query("userId") String userId);


    /**
     * 审批 同意或驳回
     *
     * @return
     */
    @PUT("cloud-app/apply/checkApply")
    Observable<String> examineApprove(@Query("userId") String userId
            , @Query("applyId") String applyId
            , @Query("applyStatus") String applyStatus
            , @Query("applyRemark") String applyRemark
            , @Header("enterpriseId") String enterpriseId);


    /**
     * 审批 撤销
     *
     * @param userId
     * @param id
     * @return
     */
    @DELETE("cloud-app/apply/recallApproval")
    Observable<String> recallApprove(@Query("userId") String userId, @Query("id") String id);


    /**
     * 聊天历史记录
     *
     * @param fromId
     * @param convrId
     * @param jCreateTime
     * @param pageSize
     * @return
     */
    @GET("messages")
    Observable<String> getPrivateChatMessage(@Query("fromId") String fromId
            , @Query("toId") String toId
            , @Query("convrId") String convrId
            , @Query("jCreateTime") long jCreateTime
            , @Query("pageSize") int pageSize);


    /*
     * 离开群组页面
     * */
    @PUT("messages/leaveGroup")
    Observable<String> leaveGroup(@Query("fromId") String fromId
            , @Query("convrId") String convrId);


    /**
     * 发送消息
     *
     * @param body
     * @return
     */
    @POST("messages")
    Observable<String> sendMessage(@Body RequestBody body);


    /**
     * @param body
     * @return
     * @人员
     */
    @PUT("messages/inGroupPerson/{userId}/{groupId}")
    Observable<String> atMembers(@Path("userId") String userId,
                                 @Path("groupId") String groupId,
                                 @Body RequestBody body);


    /**
     * 清楚@消息
     *
     * @param
     * @return
     * @人员
     */
    @PUT("messages/outGroupPerson/{userId}/{groupId}")
    Observable<String> outGroupPerson(@Path("userId") String userId,
                                      @Path("groupId") String groupId);


    /**
     * 获取聊天 图片
     *
     * @param mediaId
     * @return
     */
    @GET("resources")
    Observable<String> getImage(@Query("mediaId") String mediaId);

    /**
     * 获取群聊成员
     *
     * @param mediaId
     * @return
     */
    @GET("groups/{groupId}/members")
    Observable<String> getGroupMembers(@Path("groupId") String mediaId);


    /**
     * 上传图片
     *
     * @param userId
     * @return
     */
    @POST("resources")
    Observable<String> postImage(@Query("userId") String userId,
                                 @Body RequestBody body);


    /**
     * 上传图片
     *
     * @return
     */
    // @Headers({"Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryfIxKKABSbwXRWPwe"})//需要添加头
    @POST("cloud-app/user/upload")
    Observable<String> uploadImage(@Body RequestBody body);

//    /**
//     * 上传图片到阿里云
//     *
//     * @return
//     */
//    // @Headers({"Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryfIxKKABSbwXRWPwe"})//需要添加头
//    @POST("cloud-app/user/upload")
//    Observable<String> uploadOSSImage(@Body RequestBody body);


    /**
     * 上传大图到阿里云
     *
     * @return
     */
    // @Headers({"Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryfIxKKABSbwXRWPwe"})//需要添加头
    @POST("/cloud-common/file/upload.do")
    Observable<String> uploadBigOSSImage(@Query("tenant") int tenant, @Body RequestBody body);


    /**
     * 获取讨论社区界面的列表数据
     *
     * @param id
     * @param userId
     * @return
     */
    @GET("/work-group-questions/{id}")
    Observable<String> getDiscussMessage(@Path("id") String id, @Query("userId") String userId);

    /**
     * 获取桌面
     *
     * @param userId
     * @return
     */
    @GET("/cloud-app/application_record/list")
    Observable<String> getDesktopList(@Query("userId") String userId);


    /**
     * 获取个人信息界面
     *
     * @param id
     * @param fromUserId
     * @return
     */
    @GET("/imUsers/{id}")
    Observable<String> getUserInfo(@Path("id") String id, @Query("fromUserId") String fromUserId);


    /**
     * 获取积分列表
     *
     * @param id
     * @return
     */
    @GET("/imUsers/{id}/points")
    Observable<String> getPoints(@Path("id") String id);

    /**
     * 获取应用列表
     *
     * @param userId
     * @param pageNum
     * @param numPerPage
     * @return
     */
    @GET("/cloud-app/application_record/page")
    Observable<String> getStoreList(@Query("userId") String userId,
                                    @Query("pageNum") int pageNum,
                                    @Query("numPerPage") int numPerPage);


    /**
     * 删除或者添加应用
     *
     * @param body
     * @return
     */
    @POST("/cloud-app/application_record/addOrMove")
    Observable<String> addOrRemoveStore(@Body RequestBody body, @Header("enterpriseId") String enterpriseId);


    /**
     * 排序
     *
     * @param body
     * @return
     */
    @POST("/cloud-app/application_record/setApplicationSort/{userId}")
    Observable<String> setApplicationSort(@Path("userId") String userId, @Body RequestBody body, @Header("enterpriseId") String enterpriseId);


    /**
     * 获取我的报销列表
     *
     * @param userId
     * @param pageNum
     * @param numPerPage
     * @return
     */
    @GET("/cloud-app/apply/getStatementApply")
    Observable<String> getMineClaimingList(@Query("createdBy") String userId,
                                           @Query("pageNum") int pageNum,
                                           @Query("numPerPage") int numPerPage);


    /**
     * 获取审核人列表
     *
     * @param id
     * @return
     */
    @GET("/cloud-app/approvalUserSetting/getSettingByUserId/{id}")
    Observable<String> getApprovalUserSetting(@Path("id") String id, @Header("enterpriseId") String enterpriseId);


    /**
     * 获取币种列表
     *
     * @param enterpriseId
     * @return
     */
    @GET("/cloud-app/currency/getList")
    Observable<String> getCurrencyList(@Header("enterpriseId") String enterpriseId);

    /**
     * 获取费用类型列表
     *
     * @param enterpriseId
     * @return
     */
    @GET("/cloud-app/cost/getList")
    Observable<String> getCostList(@Header("enterpriseId") String enterpriseId);


    /**
     * 获取请假类型列表
     *
     * @param enterpriseId
     * @return
     */
    @GET("/cloud-app/type/setting/getList")
    Observable<String> getLeaveTypeList(@Header("enterpriseId") String enterpriseId);

    /**
     * 新建审批  请假 加班 公出  报销
     *
     * @param body
     * @return
     */
    @POST("/cloud-app/apply")
    Observable<String> addApply(@Body RequestBody body, @Header("enterpriseId") String enterpriseId);

    /**
     * 修改审批  请假 加班 公出  报销
     *
     * @param body
     * @return
     */
    @POST("/cloud-app/apply/updateApply")
    Observable<String> updateApply(@Body RequestBody body, @Header("enterpriseId") String enterpriseId);


    /**
     * 获取撤回审批
     *
     * @param id
     * @return
     */
    @DELETE("/cloud-app/apply/recall")
    Observable<String> recallApply(@Query("id") String id);

    /**
     * 获取撤回审批
     *
     * @param id
     * @return
     */
    @PUT("/cloud-app/apply/changeReadStatus")
    Observable<String> changeApplyReadStatus(@Query("applyId") String applyId);


    /**
     * 新建报销获取往来单位
     *
     * @param
     * @return
     */
    @GET("/cloud-enterprise/exchangeEnterprise/account/{id}")
    Observable<String> getRelationEnterprise(@Path("id") String enterprise, @Query("searchKey") String searchKey);

    /**
     * 更改报销发票状态
     *
     * @param
     * @return
     */
    @PUT("/cloud-app/apply/updateApplyInvoiceType")
    Observable<String> updateApplyInvoiceType(@Query("applyId") String applyId,
                                              @Query("applyAddressId") String applyAddressId,
                                              @Query("invoiceType") String invoiceType);

    /**
     * 更改报销发票图片
     *
     * @param
     * @return
     */
    @POST("/cloud-app/apply/updateApplyInvoice")
    Observable<String> updateApplyInvoiceImage(@Query("applyId") String applyId,
                                               @Body RequestBody requestBody
    );


    /**
     * 获取我的考勤列表 请假 加班 公出
     *
     * @param userId
     * @param pageNum
     * @param numPerPage
     * @return
     */
    @GET("/cloud-app/apply/getAttendApply")
    Observable<String> getMineAttendApply(@Query("createdBy") String userId,
                                          @Query("type") String type,
                                          @Query("pageNum") int pageNum,
                                          @Query("numPerPage") int numPerPage);


    /**
     * 获取我的当天考勤
     *
     * @param userId
     * @return
     */
    @GET("/cloud-attendance/attendRecord")
    Observable<String> getAttendRecord(@Query("recordUserId") String userId);


    /**
     * 打卡
     *
     * @return
     */
    @POST("/cloud-attendance/attendRecord")
    Observable<String> addAttendRecord(@Query("recordUserId") String recordUserId,
                                       @Query("recordType") String recordType,
                                       @Query("recordRemark") String recordRemark,
                                       @Query("wifiName") String wifiName,
                                       @Query("address") String address,
                                       @Query("recordLat") double recordLat,
                                       @Query("recordLng") double recordLng);


    /**
     * 获取日程不为空的日期
     *
     * @param userId
     * @return
     */
    @GET("/cloud-user/user/userSchedule/contentNotNullList/{userId}")
    Observable<String> getUserScheduleContentNotNullList(@Path("userId") String userId);


    /**
     * 获取当天日程
     *
     * @param userId
     * @return
     */
    @GET("/cloud-user/user/userSchedule/{userId}/{date}/")
    Observable<String> getUserSchedule(@Path("userId") String userId, @Path("date") String date);


    /**
     * 新建日程
     *
     * @param body
     * @return
     */
    @POST("/cloud-user/user/userSchedule/schedules")
    Observable<String> addSchedule(@Body RequestBody body);

    /**
     * 修改日程
     *
     * @param body
     * @return
     */
    @PUT("/cloud-user/user/userSchedule/updateUserSchedule")
    Observable<String> updateSchedule(@Body RequestBody body);

    /**
     * 删除日程
     *
     * @param userId
     * @return
     */
    @DELETE("/cloud-user/user/userSchedule/{scheduleId}")
    Observable<String> deleteSchedule(@Path("scheduleId") String scheduleId, @Query("scheduleDeleteUserId") String userId);


    /**
     * 获取当天日程
     *
     * @param userId
     * @return
     */
    @GET("/cloud-user/user/userSchedule/getScheduleDetail")
    Observable<String> getUserScheduleDetail(@Query("userId") String userId, @Query("scheduleId") String scheduleId);


    /**
     * 新建会务
     *
     * @param body
     * @return
     */
    @POST("/work-group-questions")
    Observable<String> addDiscuss(@Body RequestBody body);

    /**
     * 编辑会务
     *
     * @param body
     * @return
     */
    @PUT("/work-group-questions")
    Observable<String> editDiscuss(@Body RequestBody body);

    /**
     * 添加会务评论
     *
     * @param body
     * @return
     */
    @POST("/work-group-questions/comments")
    Observable<String> addDiscussMessage(@Body RequestBody body);


    /**
     * 会务添加人员
     *
     * @return
     */
    @POST("/work-group-questions/addUsers")
    Observable<String> discussAddUsers(@Query("questionId") String questionId, @Query("userIds") List<String> userIds);

    /**
     * 删除回去
     *
     * @return
     */
    @DELETE("/work-group-questions/{id}")
    Observable<String> deleteQuestion(@Path("id") String questionId);

    /**
     * 开启或关闭会务
     *
     * @return
     */
    @PUT("/work-group-questions")
    Observable<String> updateQuestionStatus(@Body RequestBody body);


    /**
     * 获取好友列表
     *
     * @param userId
     * @return
     */
    @GET("/imUsers/{userId}/cards")
    Observable<String> getFriendList(@Path("userId") String userId, @Query("enterpriseId") String enterpriseId);

    /**
     * 搜索平台用户
     *
     * @return
     */
    @GET("/cloud-search/es/users")
    Observable<String> searchNewUserList(@Query("isHighlight") String isHighlight,
                                         @Query("searchKey") String searchKey,
                                         @Query("page") int page,
                                         @Query("pageSize") int pageSize);

    /**
     * 删除好友
     *
     * @return
     */
    @DELETE("/friendApplies/")
    Observable<String> deleteFriend(@Query("userId") String userId,
                                    @Query("friendUserId") String friendUserId);


    /**
     * 发送好友请求
     *
     * @return
     */
    @POST("/friendApplies/")
    Observable<String> postFriendApplies(@Query("fromUserId") String fromUserId,
                                         @Query("toUserId") String toUserId);

    /**
     * 同意好友请求
     *
     * @return
     */
    @PUT("/friendApplies/")
    Observable<String> agreeFriendApplies(@Query("applyId") String applyId,
                                          @Query("status") int status,
                                          @Query("fromUserId") String fromUserId,
                                          @Query("fromEnterpriseId") String fromEnterpriseId,
                                          @Query("toUserId") String toUserId,
                                          @Query("toEnterpriseId") String toEnterpriseId
    );


    /**
     * 获取发票列表
     *
     * @return
     */
    @POST("/cloud-finance/finance/invoices")
    Observable<String> getInvoiceList(@Body RequestBody body);

    /**
     * 更改发票进销项
     *
     * @return
     */
    @PUT("/cloud-finance/finance/invoice")
    Observable<String> modifyInvoiceInOutStatus(@Body RequestBody body);

    /**
     * 扫描发票二维码获取发票详情
     *
     * @return
     */
    @GET("/cloud-finance/finance/invoice/by-qr-code")
    Observable<String> getInvoiceDetailByScan(@Query("scanStr") String scanStr, @Query("creatorId") String creatorId);

    /**
     * 获取发票收付款方
     *
     * @return
     */
    @GET("/cloud-enterprise/exchangeEnterprise/account/{enterpriseId}")
    Observable<String> getInvoiceDetailByScan(@Path("enterpriseId") String enterpriseId);

    /**
     * 手动或者扫码新增发票
     *
     * @return
     */
    @POST("/cloud-finance/finance/invoice")
    Observable<String> addInvoice(@Body RequestBody body);

    /**
     * 获取发票详情
     *
     * @return
     */
    @GET("/cloud-finance/finance/invoice/{id}")
    Observable<String> getInvoiceDetail(@Path("id") String id);


    /**
     * 获取报价列表
     *
     * @return
     */
    @POST("/cloud-order/quote/getQuoteByParams")
    Observable<String> getQuoteList(@Body RequestBody body);

    /**
     * 获取报价详情
     *
     * @return
     */
    @GET("/cloud-order/quote/getQuoteDetail")
    Observable<String> getQuoteDetail(@Query("quoteId") String quoteId);


    /**
     * 获取物品列表
     *
     * @return
     */
    @POST("/cloud-es-search/goods/search")
    Observable<String> getGoodsList(@Body RequestBody body, @Header("enterpriseId") String enterpriseId);

    /**
     * 获取物品类型列表
     *
     * @return
     */
    @GET("/cloud-wms/goodsType")
    Observable<String> getGoodsTypeList(@Query("enterpriseId") String enterpriseId);

    /**
     * 获取物品详情
     *
     * @return
     */
    @GET("/cloud-wms/goods")
    Observable<String> getGoodsDetail(@Query("enterpriseId") String enterpriseId, @Query("id") String id);

    /**
     * 获取物品报价记录
     *
     * @return
     */
    @POST("/cloud-wms/offer/page")
    Observable<String> getGoodsQuoteRecordList(@Body RequestBody body,
                                               @Query("page") int page,
                                               @Query("pageSize") int pageSize);


    /**
     * 获取物品库存记录
     *
     * @return
     */
    @GET("/cloud-wms/stockApplication/in/{id}/stock")
    Observable<String> getGoodsStockDetail(@Path("id") String id);


    /**
     * 获取仓库列表
     *
     * @return
     */
    @POST("/cloud-wms/wareHouse/getWareHouseByParams")
    Observable<String> getWarehouseList(@Body RequestBody body, @Header("enterpriseId") String enterpriseId);

    /**
     * 获取仓库库存
     *
     * @return
     */
    @GET("/cloud-wms/wareHouse/{id}")
    Observable<String> getWarehouseStockList(@Path("id") String id);

    /**
     * 获取仓库物流
     *
     * @return
     */
    @GET("/cloud-wms/wareHouse/stockApplication/{id}")
    Observable<String> getWarehouseStockApplication(@Path("id") String id,
                                                    @Query("page") int page,
                                                    @Query("pageSize") int pageSize);


    /**
     * 获取订单列表
     *
     * @return
     */
    @POST("/cloud-order/order/getOrdersByParams")
    Observable<String> getRequirementList(@Body RequestBody body, @Header("enterpriseId") String enterpriseId);

    /**
     * 获取订单数量
     *
     * @return
     */
    @GET("/cloud-order/order/get")
    Observable<String> getOrderNumber(@Query("id") String id);


    /**
     * 获取需求列表
     *
     * @return
     */
    @POST("/cloud-order/requirement/listPage")
    Observable<String> getOrderList(@Body RequestBody body, @Header("enterpriseId") String enterpriseId);

    /**
     * 获取合同列表
     *
     * @return
     */
    @POST("/cloud-order/contract/getContractByParams")
    Observable<String> getContractList(@Body RequestBody body, @Header("enterpriseId") String enterpriseId);

    /**
     * 获取合同详情
     *
     * @return
     */
    @GET("/cloud-order/contract/get")
    Observable<String> getContractDetail(@Query("id") String id);


    /**
     * 获商圈列表
     *
     * @return
     */
    @GET("/dataProfile/v2")
    Observable<String> getBusinessList();

    /**
     * 获取原材料价格
     *
     * @return
     */
    @GET("/weavePrice/list")
    Observable<String> getMaterialsPrice(@Query("date") String date, @Query("type") String type, @Query("name") String name);

    /**
     * 获取汇率
     *
     * @return
     */
    @POST("/cloud-crawler/crawler/getRateCurrencyList")
    Observable<String> getRateCurrency(@Body RequestBody body);

    /**
     * 获取汇率及原材料类型
     *
     * @return
     */
    @POST("/cloud-crawler/crawler/getPriceType")
    Observable<String> getPriceType(@Query("userId") String userId, @Query("momentsType") String momentsType);

    /**
     * 添加自定义汇率及原材料类型
     *
     * @return
     */
    @POST("/cloud-crawler/crawler/addTypeList/{userId}/{momentsType}")
    Observable<String> addBusinessTypeList(@Path("userId") String userId, @Path("momentsType") String momentsType, @Body RequestBody body);

    /**
     * 获取历史原材料数据
     *
     * @return
     */
    @POST("/cloud-crawler/crawler/getPriceListHistory/{date}")
    Observable<String> getMaterialsHistoryPrice(@Path("date") String date, @Body RequestBody body);

    /**
     * 获取历史汇率数据
     *
     * @return
     */
    @POST("/cloud-crawler/crawler/getRateCurrencyListHistory/{date}")
    Observable<String> getRateHistoryPrice(@Path("date") String date, @Body RequestBody body);


    /**
     * 创建群聊
     *
     * @return
     */
    @POST("/groups")
    Observable<String> addGroupChat(@Query("owner") String owner,
                                    @Query("groupName") String groupName,
                                    @Query("userIds") String userIds,
                                    @Query("desc") String desc);

    /**
     * 群聊添加成员
     *
     * @return
     */
    @POST("/groups/{groupId}/members")
    Observable<String> addGroupMembers(@Path("groupId") String groupId,
                                       @Query("addUserIds") String addUserIds);

    /**
     * 删除群成员
     *
     * @return
     */
    @POST("/groups/{groupId}/members")
    Observable<String> deleteGroupMembers(@Path("groupId") String groupId,
                                          @Query("removeUserIds") String addUserIds);


    /**
     * 获取仓库物流
     *
     * @return
     */
    @GET("/cloud-search/es/contacts/{userId}")
    Observable<String> friendSearch(@Path("userId") String userId,
                                    @Query("searchKey") String searchKey,
                                    @Query("isHighlight") String isHighlight,
                                    @Query("page") int page,
                                    @Query("pageSize") int pageSize);


    /**
     * 获取人员列表
     *
     * @return
     */
    @GET("/cloud-user/{enterpriseId}/users")
    Observable<String> getEmployeeList(@Path("enterpriseId") String enterpriseId,
                                       @Query("page") int page,
                                       @Query("pageSize") int pageSize);

    /**
     * 获取成员申请列表
     *
     * @return
     */
    @GET("cloud-user/getApplyUserList/{enterpriseId}")
    Observable<String> getEmployeeApplyList(@Path("enterpriseId") String enterpriseId);

    /**
     * 处理成员申请
     *
     * @return
     */
    @PUT("/cloud-user/subsequentOperation")
    Observable<String> handleEmployeeApply(@Query("id") String id,
                                           @Query("operateType") String operateType,
                                           @Header("enterpriseId") String enterpriseId);


    /**
     * 获取聊天页面底部功能按钮
     *
     * @return
     */
    @GET("/cloud-app/applyImage/list/{userId}/{module}")
    Observable<String> getChatBottomItem(@Path("userId") String userId,
                                         @Path("module") int module);

    /**
     * 聊天页面底部功能按钮排序
     *
     * @return
     */
    @POST("/cloud-app/applyImage/setSort/{userId}")
    Observable<String> chatBottomItemSort(@Path("userId") String userId,
                                          @Body RequestBody body);


    /**
     * 天眼查列表
     *
     * @return
     */
    @GET("/third-party/tycCompany")
    Observable<String> enterpriseSearchList(@Query("word") String word);
    /**
     * 天眼查详情
     *
     * @return
     */
    @GET("/third-party/tycCompany/{id}")
    Observable<String> enterpriseDetail(@Path("id") String id);


}
