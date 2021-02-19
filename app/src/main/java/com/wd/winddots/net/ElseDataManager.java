package com.wd.winddots.net;



import okhttp3.RequestBody;
import rx.Observable;

public class ElseDataManager {
    private RetrofitService mRetrofitService;
    public ElseDataManager(){
        this.mRetrofitService = ElseRetrofitHelper.getInstance().getServer();
    }

    /**
     * 登录
     * @param body
     * @return
     */
    public Observable<String> login( RequestBody body){
        return mRetrofitService.login(body);
    }



    /**
     * 消息 审批 列表
     * @param id
     * @param page
     * @param pageSize
     * @return
     */
    public Observable<String> loadExamineApproveListData(String id,int page,int pageSize){
        return mRetrofitService.loadExamineApproveListData(id,page,pageSize);
    }

    /**
     * 审批流程详细数据
     * @param id
     * @param userId
     * @return
     */
    public Observable<String> loadApprovalPocessData(String id,String userId){
        return mRetrofitService.loadApprovalPocessData(id,userId);
    }

    /**
     * 审批 同意或驳回
     * @param userId
     * @param applyId
     * @param applyStatus
     * @param applyRemark
     * @return
     */
    public Observable<String> examineApprove(String userId,String applyId,String applyStatus,String applyRemark,String enterpriseId){
        return mRetrofitService.examineApprove(userId,applyId,applyStatus,applyRemark,enterpriseId);
    }

    /**
     * 审批 撤回
     * @param userId
     * @param id
     * @return
     */
    public Observable<String> recallApprove(String userId,String id){
        return mRetrofitService.recallApprove(userId,id);
    }

    /**
     * 获取桌面列表
     * @param userId
     * @return
     */
    public Observable<String> getDesktopList(String userId){
        return mRetrofitService.getDesktopList(userId);
    }

    /**
     * 获取获取应用市场
     * @param userId
     * @return
     */
    public Observable<String> getStoreList(String userId,Integer pageNum,Integer numPerPage ){
        return mRetrofitService.getStoreList(userId,pageNum,numPerPage);
    }


    /**
     * 删除或添加办公 item
     * @return
     */
    public Observable<String> addOrRemoveStore(RequestBody body,String enterpriseId){
        return mRetrofitService.addOrRemoveStore(body,enterpriseId);
    }


    /**
     * 删除或添加办公 item
     * @return
     */
    public Observable<String> getMineClaimingList(String userId,Integer pageNum,Integer numPerPage ){
        return mRetrofitService.getMineClaimingList(userId,pageNum,numPerPage);
    }

    /**
     * 排序
     * @return
     */
    public Observable<String> setApplicationSort(String userId,RequestBody body,String enterpriseId ){
        return mRetrofitService.setApplicationSort(userId,body,enterpriseId);
    }


    /**
     * 获取审核人列表
     * @return
     */
    public Observable<String> getApprovalUserSetting(String userId,String enterpriseId){
        return mRetrofitService.getApprovalUserSetting(userId,enterpriseId);
    }

    /**
     * 获取币种列表
     * @return
     */
    public Observable<String> getCurrencyList(String enterpriseId){
        return mRetrofitService.getCurrencyList(enterpriseId);
    }

    /**
     * 获取请假类型列表
     * @return
     */
    public Observable<String> getLeaveTypeList(String enterpriseId){
        return mRetrofitService.getLeaveTypeList(enterpriseId);
    }

    /**
     * 获取费用类型列表
     * @return
     */
    public Observable<String> getCostList(String enterpriseId){
        return mRetrofitService.getCostList(enterpriseId);
    }


    /**
     * 新建审批
     * @return
     */
    public Observable<String> addApply(RequestBody body,String enterpriseId){
        return mRetrofitService.addApply(body,enterpriseId);
    }

    /**
     * 新建审批
     * @return
     */
    public Observable<String> changeApplyReadStatus(String applyId){
        return mRetrofitService.changeApplyReadStatus(applyId);
    }

    /**
     * 编辑审批
     * @return
     */
    public Observable<String> updateApply(RequestBody body,String enterpriseId){
        return mRetrofitService.updateApply(body,enterpriseId);
    }

    /**
     * 编辑审批
     * @return
     */
    public Observable<String> recallApply(String id){
        return mRetrofitService.recallApply(id);
    }


    /**
     * 上传图片 返回缩略图  带 http
     * @return
     */
    public Observable<String> uploadImage(RequestBody body){
        return mRetrofitService.uploadImage(body);
    }

//    /**
//     * 上传图片 到阿里云
//     * @return
//     */
//    public Observable<String> uploadOSSImage(RequestBody body){
//        return mRetrofitService.uploadOSSImage(body);
//    }

    /**
     * 上传图片 到阿里云
     * @return
     */
    public Observable<String> uploadBigOSSImage(RequestBody body){
        return mRetrofitService.uploadBigOSSImage(1,body);
    }


    /**
     * 获取报销往来单位账号
     * @return
     */
    public Observable<String> getRelationEnterprise(String enterprise,String searchKey){
        return mRetrofitService.getRelationEnterprise(enterprise,searchKey);
    }

    /**
     * 更改发票开票类型
     * @return
     */
    public Observable<String> updateApplyInvoiceType(String applyId,String applyAddressId,String invoiceType){
        return mRetrofitService.updateApplyInvoiceType(applyId,applyAddressId,invoiceType);
    }

    /**
     * 更改发票照片
     * @return
     */
    public Observable<String> updateApplyInvoiceImage(String applyId,RequestBody requestBody){
        return mRetrofitService.updateApplyInvoiceImage(applyId,requestBody);
    }

    /**
     * 获取我的考勤列表 请假 加班 公出
     * @return
     */
    public Observable<String> getMineAttendApply(String userId,String type,int pageNum,int numPerPage){
        return mRetrofitService.getMineAttendApply(userId,type,pageNum,numPerPage);
    }

    /**
     * 获取我的考勤
     * @return
     */
    public Observable<String> getAttendRecord(String userId){
        return mRetrofitService.getAttendRecord(userId);
    }

    /**
     * 打卡
     * @return
     */
    public Observable<String> addAttendRecord(String recordUserId,
                                              String recordType,
                                              String recordRemark,
                                              String wifiName,
                                              String address,
                                              double recordLat,
                                              double recordLng){
        return mRetrofitService.addAttendRecord(recordUserId,recordType,recordRemark,wifiName,address,recordLat,recordLng);
    }



    /**
     * 获取我的考勤
     * @return
     */
    public Observable<String> getUserSchedule(String userId,String date){
        return mRetrofitService.getUserSchedule(userId,date);
    }



    /**
     * 新建日程
     * @return
     */
    public Observable<String> getUserScheduleContentNotNullList(String userId){
        return mRetrofitService.getUserScheduleContentNotNullList(userId);
    }


    /**
     * 新建日程
     * @return
     */
    public Observable<String> addSchedule(RequestBody requestBody){
        return mRetrofitService.addSchedule(requestBody);
    }

    /**
     * 新建日程
     * @return
     */
    public Observable<String> updateSchedule(RequestBody requestBody){
        return mRetrofitService.updateSchedule(requestBody);
    }


    /**
     * 获取考勤详情
     * @return
     */
    public Observable<String> deleteSchedule(String scheduleId,String userId){
        return mRetrofitService.deleteSchedule(scheduleId,userId);
    }

    /**
     * 获取考勤详情
     * @return
     */
    public Observable<String> getUserScheduleDetail(String userId,String scheduleId){
        return mRetrofitService.getUserScheduleDetail(userId,scheduleId);
    }

    /**
     * 搜索平台用户
     * @return
     */
    public Observable<String> searchNewUserList(String isHighlight,String searchKey,int page,int pageSize){
        return mRetrofitService.searchNewUserList(isHighlight,searchKey,page,pageSize);
    }

    /**
     * 获取发票列表
     * @return
     */
    public Observable<String> getInvoiceList(RequestBody body){
        return mRetrofitService.getInvoiceList(body);
    }

    /**
     * 获取发票列表
     * @return
     */
    public Observable<String> getInvoiceDetailByScan(String scanStr,String creatorId){
        return mRetrofitService.getInvoiceDetailByScan(scanStr,creatorId);
    }

    /**
     * 更改发票进销项
     * @return
     */
    public Observable<String> modifyInvoiceInOutStatus(RequestBody body){
        return mRetrofitService.modifyInvoiceInOutStatus(body);
    }

    /**
     * 新增发票
     * @return
     */
    public Observable<String> addInvoice(RequestBody body){
        return mRetrofitService.addInvoice(body);
    }

    /**
     * 获取发票详情
     * @return
     */
    public Observable<String> getInvoiceDetail(String id){
        return mRetrofitService.getInvoiceDetail(id);
    }

    /**
     * 获取报价列表
     * @return
     */
    public Observable<String> getQuoteList(RequestBody body){
        return mRetrofitService.getQuoteList(body);
    }

    /**
     * 获取报价详情
     * @return
     */
    public Observable<String> getQuoteDetail(String quoteId){
        return mRetrofitService.getQuoteDetail(quoteId);
    }


    /**
     * 获取物品列表
     * @return
     */
    public Observable<String> getGoodsList(RequestBody body,String enterpriseId){
        return mRetrofitService.getGoodsList(body,enterpriseId);
    }

    /**
     * 获取物品类型列表
     * @return
     */
    public Observable<String> getGoodsTypeList(String enterpriseId){
        return mRetrofitService.getGoodsTypeList(enterpriseId);
    }

    /**
     * 获取物品类型详情
     * @return
     */
    public Observable<String> getGoodsDetail(String enterpriseId,String id){
        return mRetrofitService.getGoodsDetail(enterpriseId,id);
    }

    /**
     * 获取物品报价记录
     * @return
     */
    public Observable<String> getGoodsQuoteRecordList(RequestBody body,int page,int pageSize){
        return mRetrofitService.getGoodsQuoteRecordList(body,page,pageSize);
    }

    /**
     * 获取物品报价记录
     * @return
     */
    public Observable<String> getGoodsStockDetail(String id){
        return mRetrofitService.getGoodsStockDetail(id);
    }

    /**
     * 获取仓库列表
     * @return
     */
    public Observable<String> getWarehouseList(RequestBody body,String enterpriseId){
        return mRetrofitService.getWarehouseList(body,enterpriseId);
    }

    /**
     * 获取仓库列表
     * @return
     */
    public Observable<String> getWarehouseStockList(String id){
        return mRetrofitService.getWarehouseStockList(id);
    }

    /**
     * 获取仓库物流
     * @return
     */
    public Observable<String> getWarehouseStockApplication(String id,int page,int pageSize){
        return mRetrofitService.getWarehouseStockApplication(id,page,pageSize);
    }

    /**
     * 获取订单列表
     * @return
     */
    public Observable<String> getOrderList(RequestBody body,String enterpriseId){
        return mRetrofitService.getOrderList(body,enterpriseId);
    }

    /**
     * 获取订单数量
     * @return
     */
    public Observable<String> getOrderNumber(String id){
        return mRetrofitService.getOrderNumber(id);
    }


    /**
     * 获取需求列表
     * @return
     */
    public Observable<String> getRequirementList(RequestBody body,String enterpriseId){
        return mRetrofitService.getRequirementList(body,enterpriseId);
    }

    /**
     * 获取合同列表
     * @return
     */
    public Observable<String> getContractList(RequestBody body,String enterpriseId){
        return mRetrofitService.getContractList(body,enterpriseId);
    }

    /**
     * 获取合同列表
     * @return
     */
    public Observable<String> getContractDetail(String id){
        return mRetrofitService.getContractDetail(id);
    }



    /**
     * 搜索好友列表
     *
     * @param
     * @return
     */
    public Observable<String> friendSearch(String userId,
                                           String searchKey,
                                           String isHighlight,
                                           int page,
                                           int pageSize) {
        return mRetrofitService.friendSearch(userId,searchKey,isHighlight,page,pageSize);
    }


    /**
     * 获取人员列表
     *
     * @param
     * @return
     */
    public Observable<String> getEmployeeList(String enterpriseId,
                                           int page,
                                           int pageSize) {
        return mRetrofitService.getEmployeeList(enterpriseId,page,pageSize);
    }

    /**
     * 获取成员申请
     *
     * @param
     * @return
     */
    public Observable<String> getEmployeeApplyList(String enterpriseId) {
        return mRetrofitService.getEmployeeApplyList(enterpriseId);
    }

    /**
     * 处理成员申请
     *
     * @param
     * @return
     */
    public Observable<String> handleEmployeeApply(String id,String operateType,String enterpriseId) {
        return mRetrofitService.handleEmployeeApply(id,operateType,enterpriseId);
    }

    /**
     * 获取聊天页面底部 item
     *
     * @param
     * @return
     */
    public Observable<String> getChatBottomItem(String userId,int module) {
        return mRetrofitService.getChatBottomItem(userId,module);
    }

    /**
     * 获取聊天页面底部 item
     *
     * @param
     * @return
     */
    public Observable<String> chatBottomItemSort(String userId,RequestBody body) {
        return mRetrofitService.chatBottomItemSort(userId,body);
    }











}
