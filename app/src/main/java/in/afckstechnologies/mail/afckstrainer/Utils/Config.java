package in.afckstechnologies.mail.afckstrainer.Utils;

import java.util.ArrayList;

/**
 * Created by admin on 12/9/2016.
 */

public class Config {
    // public static final String BASE_URL = "http://192.168.1.10:80/afcks/api/";
    // public static final String BASE_URL = "http://testprojects.in/afcks/api/";
    public static final String BASE_URL = "https://afckstechnologies.in/afcks/api/";
    public static final String URL_STUDENT_REGISTRATION = BASE_URL + "user/adduserByAdmin";
    public static final String URL_CENTER_DETAILS = BASE_URL + "user/branches";
    public static final String URL_COURSE_DETAILS = BASE_URL + "user/cources";
    public static final String URL_SEND_DETAILS = BASE_URL + "user/savecourse";
    public static final String URL_SEND_LOCATION_DETAILS = BASE_URL + "user/savebranch";
    public static final String URL_BANK_DETAILS = BASE_URL + "user/getbankdetails";
    public static final String URL_GET_USER_DETAILS = BASE_URL + "user/getuserdetails";
    public static final String URL_UPDATE_USER_DETAILS = BASE_URL + "user/updateuserdetails";
    public static final String URL_COURSE_DETAILS_BY_USERID = BASE_URL + "user/courcesbyuserid";
    public static final String URL_BATCHES_DETAILS = BASE_URL + "user/batches";
    public static final String URL_BOOKING_BATCH = BASE_URL + "user/bookingbatchByAdmin";
    public static final String URL_DELETE_COURSE = BASE_URL + "user/deleteusercource";
    public static final String URL_DELETE_CENTER = BASE_URL + "user/deleteuserbranch";
    public static final String URL_DISPLAY_CENTER = BASE_URL + "user/getallbranches";
    public static final String URL_DISPLAY_COURSES = BASE_URL + "user/getallCoursesById";
    public static final String URL_DISPLAY_STUDENTS = BASE_URL + "user/getallStudentsByBatchID";
    public static final String URL_DISPLAY_COURSES_LOCATION = BASE_URL + "user/getallCoursesByLocId";
    public static final String URL_DISPLAY_DEMAND_STUDENTS = BASE_URL + "user/getallDemandStudentsById";
    public static final String URL_ADDTEMPLATE = BASE_URL + "user/addTemplates";
    public static final String URL_VIEWTEMPLATE = BASE_URL + "user/getallTemplates";
    public static final String URL_GETALLBATCHTRAINERBYPREFIX = BASE_URL + "user/getallBatchTrainerByPreFix";
    public static final String URL_USER_AVAILABLE = BASE_URL + "user/availableTrainer";
    public static final String URL_UPDATE_TRAINER_DETAILS = BASE_URL + "user/updatetrainerdetails";
    public static final String URL_AVAILABLE_MOBILE_DEVICEID = BASE_URL + "user/getAvailableMobileDeviceID";
    public static final String URL_STUDENT_REGISTRATION_BY_TRAINER_ADMIN = BASE_URL + "user/adduserUsingStoreProcdureByAdmin";
    public static final String URL_ADD_STUDENT_FEESDETAILS = BASE_URL + "user/addStudentFeesDetails";
    public static final String URL_GETSTUDENTCASHBACK = BASE_URL + "user/getStudentCashBack";
    public static final String URL_GETALLSTUDENTSBYID1 = BASE_URL + "user/getallStudentsByPreFix";
    public static final String URL_ADD_STUDENT_INBATCH = BASE_URL + "user/addStudentInBatch";
    public static final String URL_GETALL_FEES_DETAILSINBATCH = BASE_URL + "user/getallFeesDetailsInBatch";
    public static final String URL_DELETE_TEMPLATE = BASE_URL + "user/deleteTemplate";
    public static final String URL_ADDTEMPLATESCONTACTS = BASE_URL + "user/addTemplatesContacts";
    public static final String URL_GETALLCONTACTTEMPLATES = BASE_URL + "user/getallContactTemplates";
    public static final String URL_DELETECONTACTTEMPLATE = BASE_URL + "user/deleteContactTemplate";
    public static final String URL_UPDATECONTACTTEMPLATEDETAILS = BASE_URL + "user/updatecontacttemplatedetails";
    public static final String URL_TAKEATTENDENCEBYBTACH = BASE_URL + "user/takeAttendenceByBtach";
    public static final String URL_DISCONTINUEBATCHSTUDENT = BASE_URL + "user/discontinueBatchStudent";
    public static final String URL_DELETEPRESENTDETAILS = BASE_URL + "user/deletePresentDetails";
    public static final String URL_AVAILABLESTUDENT = BASE_URL + "user/availableStudent";
    public static final String URL_DISPLAY_FEEDBACK_STUDENTS = BASE_URL + "user/getStudentFeedbackDetails";
    public static final String URL_GETALLBACTHCODEOFFEEDBACK = BASE_URL + "user/getallBacthCodeOfFeedback";
    public static final String URL_GETALLFEEDBACKDATEOFFEEDBACK = BASE_URL + "user/getallFeedBackDateOfFeedback";
    public static final String URL_GETALLSTATES = BASE_URL + "user/getallStates";
    public static final String URL_GETALLCITY = BASE_URL + "user/getallCity";
    public static final String URL_ADD_COMPANY_DETAILS = BASE_URL + "user/addCompanyDetails";
    public static final String URL_GETALLCOMPANY = BASE_URL + "user/getallCompany";
    public static final String URL_GETALLCOMPANYBYID = BASE_URL + "user/getallCompanyById";
    public static final String URL_UPDATECORPORATESTUDENT = BASE_URL + "user/updateCorporateStudent";
    public static final String URL_UPDATEMOSSTUDENT = BASE_URL + "user/updateMOSStudent";
    public static final String URL_GET_GST_DETAILSBYPROC = BASE_URL + "user/getGStDetailsByProc";
    public static final String URL_GETSTUDENTSCASHBACK = BASE_URL + "user/getStudentsCashBack";
    public static final String URL_GETFULLFEESSTATUS = BASE_URL + "user/getFullFeesStatus";
    public static final String URL_UPLOAD_RECEIPT_URL = "http://www.afckstechnologies.in/Studentdocuments/upload_receipts.php";
    public static final String URL_UPLOAD_LETTER_URL = "http://www.afckstechnologies.in/Studentdocuments/upload_letters.php";
    public static final String URL_UPLOAD_AFCKSNOTES_URL = "http://afckstechnologies.in/AFCKS_Notes/fileUploadAFCKSNotes.php";
    public static final String URL_UPDATEUSERCASHBACKDETAILS = BASE_URL + "user/updateUserCashbackDetails";
    public static final String URL_DISCONTINUENOTESSTUDENT = BASE_URL + "user/discontinueNotesStudent";
    public static final String URL_GETALLATTENDANCEDETAILSINBATCH = BASE_URL + "user/getallAttendanceDetailsInBatch";
    public static final String URL_UPDATEBATCHEND = BASE_URL + "user/updateBatchEnd";
    public static final String URL_GETSTUDENTDISCONTINUEDETAILSINBATCH = BASE_URL + "user/getStudentDiscontinueDetailsInBatch";
    public static final String URL_ADDREQUESTTYPE = BASE_URL + "user/addRequestType";
    public static final String URL_GETALLREQUESTTYPENAME = BASE_URL + "user/getallRequestTypeName";
    public static final String URL_GETALLREQUESTUSERNAME = BASE_URL + "user/getallRequestUserName";
    public static final String URL_ADDREUSERCHANGEDETAILS = BASE_URL + "user/addReuserChangeDetails";
    public static final String URL_ADDREQUESTATTACHMENT = BASE_URL + "user/addRequestAttachment";
    public static final String URL_GETALLREQUESTCHANGEBYUSERID = BASE_URL + "user/getallRequestChangeByUserID";
    public static final String URL_GETALLREQUESTCHANGEATTACHBYUSERID = BASE_URL + "user/getallRequestChangeAttachByUserID";
    public static final String URL_GETALLREQUESTPENDDINGBYUSERID = BASE_URL + "user/getallRequestPenddingByUserID";
    public static final String URL_UPDATEREQUESTCHANGEDETAILS = BASE_URL + "user/updateRequestChangeDetails";
    public static final String URL_GETCOUNTCALLBACK = BASE_URL + "user/getCountCallingUsers";
    public static final String URL_GETCOUNTFEEDBACK = BASE_URL + "user/getCountFeedback";
    public static final String URL_GETALLSTUDENTSBYID = BASE_URL + "user/getallStudentsById";
    public static final String URL_ADDUSERCONTACTDETAILS = BASE_URL + "user/addUserContactDetails";
    public static final String URL_UPDATEUSERCALLCOMMENTDETAILS = BASE_URL + "user/updateUserCallCommentDetails";
    public static final String URL_UPDATE_USER_COMMENT_DETAILS = BASE_URL + "user/updateUserCommentDetails";
    public static final String URL_UPDATEUSERCOMMENTPREDETAILS = BASE_URL + "user/updateUserCommentPreDetails";
    public static final String URL_GETUSERCOMMENTDETAILS = BASE_URL + "user/getUserCommentDetails";
    public static final String URL_GETUSERCOMMENTPREDETAILS = BASE_URL + "user/getUserCommentPreDetails";
    public static final String URL_STUDENT_DELETE = BASE_URL + "user/deleteUser";
    public static final String URL_GETALLCOURSESBYUSERID = BASE_URL + "user/getallCoursesByUserId";
    public static final String URL_GETALLLOCATIONSBYUSERID = BASE_URL + "user/getallLocationsByUserId";
    public static final String URL_DISPLAY_GETALLBATCHESBYID = BASE_URL + "user/getallBatchesById";
    public static final String URL_DAYPREFRENCE_DETAILS = BASE_URL + "user/dayprefrence";
    public static final String URL_SEND_DAYPREFRENCE_DETAILS = BASE_URL + "user/savedayprefrence";
    public static final String URL_DELETE_DAYPREFRENCE = BASE_URL + "user/deletedayprefrence";
    public static final String URL_GETPRESTUDENTSDETAILS = BASE_URL + "user/getPreStudentsDetails";
    public static final String URL_GETALLSTUDENTSBYPREFIX = BASE_URL + "user/getallStudentsByPreFix";
    public static final String URL_DISPLAY_DEMAND_COURSES_LOCATION = BASE_URL + "user/getallDemandCoursesByLocId";
    public static final String URL_DELETE_STBATCH_BOOKING = BASE_URL + "user/deleteStBatchBooking";
    public static final String URL_GETALLBOOKEDBATCHBYBATCHID_DETAILS = BASE_URL + "user/getallBookedBatchByBatchId";
    public static final String URL_UPDATEBATCHDETAILS = BASE_URL + "user/updatebatchdetails";
    public static final String URL_GETALLBATCHMODIFYBYPREFIX = BASE_URL + "user/getallBatchModifyByPreFix";
    public static final String URL_GETUSERSFEEDBACKDETAILS = BASE_URL + "user/getUsersFeedbackDetails";
    public static final String URL_UPDATEUSERFEEDBACKS = BASE_URL + "user/updateUserFeedbacks";
    public static final String URL_APPROVEUSERFEEDBACKS = BASE_URL + "user/approveUserFeedbacks";
    public static final String URL_DELETEUSERSFEEDBACK = BASE_URL + "user/deleteUsersFeedback";
    public static final String URL_UPDATEUSERCALLINGDETAILS = BASE_URL + "user/updateUserCallingDetails";
    public static final String URL_GETCALLINGUSERDETAILS = BASE_URL + "user/getCallingUserDetails";
    public static final String URL_GETALLSTUDENTFORFEERECONCILILATION = BASE_URL + "user/getallStudentForFeeReconcililation";
    public static final String URL_GETALLRECONBANKDETAILS = BASE_URL + "user/getallReconBankDetails";
    public static final String URL_GETALLBANKDETAILSRECONCILILATION = BASE_URL + "user/getallBankDetailsReconcililation";
    public static final String URL_UPDATEFEESRECONSTUDENT = BASE_URL + "user/updateFeesReconStudent";
    public static final String URL_ADDABRANDNAME = BASE_URL + "user/addABrandName";
    public static final String URL_GETALLAITEMNAME = BASE_URL + "user/getallAItemName";
    public static final String URL_GETALLABRANDNAME = BASE_URL + "user/getallABrandName";
    public static final String URL_ADDAITEMNAME = BASE_URL + "user/addAItemName";
    public static final String URL_GETALLACCESSORYDETAILS = BASE_URL + "user/getallAccessoryDetails";
    public static final String URL_ADDMOREACCESSORYDETAILS = BASE_URL + "user/addMoreAccessoryDetails";
    public static final String URL_GETACCESSORYDETAILS = BASE_URL + "user/getAccessoryDetails";
    public static final String URL_GETALLFITEMNAME = BASE_URL + "user/getallFItemName";
    public static final String URL_GETALLFLOCATIONNAME = BASE_URL + "user/getallFLocationName";
    public static final String URL_GETALLFSTATUSNAME = BASE_URL + "user/getallFStatusName";
    public static final String URL_GETALLVIEWACCESSORIES = BASE_URL + "user/getallViewAccessories";
    public static final String URL_GETALLANTIKEYSDETAILSBYPREFIX = BASE_URL + "user/getallAntiKeysDetailsByPreFix";
    public static final String URL_UPDATEANTIVAIRUSDETALS = BASE_URL + "user/updateAntivairusDetals";
    public static final String URL_UPDATECALLBACKSTUDENT = BASE_URL + "user/updateCallbackStudent";
    public static final String URL_ADDMOREASSESTSDETAILS = BASE_URL + "user/addMoreAssestsDetails";
    public static final String URL_ADDIDENTIFICATIONDETAILS = BASE_URL + "user/addIdentificationDetails";
    public static final String URL_UPDATEIDENTIFICATIONDETAILS = BASE_URL + "user/updateidentificationdetails";
    public static final String URL_GETALLFIXEDASSESTS = BASE_URL + "user/getallFixedAssests";
    public static final String URL_ADDFLOCATIONNAME = BASE_URL + "user/addFLocationName";
    public static final String URL_ADDFITEMNAME = BASE_URL + "user/addFItemName";
    public static final String URL_ADDEMPLOYEEATTENDACE = BASE_URL + "user/addEmployeeAttendace";
    public static final String URL_GETALLEMPLOYEEATTENDANCEBYUSERID = BASE_URL + "user/getallEmployeeAttendanceByUserID";
    public static final String URL_AVAILABLERANGE = BASE_URL + "user/availableRange";
    public static final String URL_DELETEREQUESTTICKET = BASE_URL + "user/deleteRequestTicket";
    public static final String URL_GETALLEMPLOYEEAMONTH = BASE_URL + "user/getallEmployeeAMonth";
    public static final String URL_GETUSERNAMEPASSSMS = BASE_URL + "user/getUserNamePassSMS";
    public static final String URL_ADDTEMPLOCATIONDATA = BASE_URL + "user/addTempLocationData";

    public static final String URL_GETALLREQUESTEMPLOYEENAME = BASE_URL + "user/getallRequestEmployeeName";
    public static final String URL_GETALLREQUESTPENDDINGUSERNAME = BASE_URL + "user/getallRequestPenddingUserName";
    public static final String URL_GETALLEMPLOYEEDATES = BASE_URL + "user/getallEmployeeDates";
    public static final String URL_UPDATEEMPATTENDETAILS = BASE_URL + "user/updateEmpAttenDetails";
    public static final String URL_UPDATEEMPATTENDETAILSBYADMIN = BASE_URL + "user/updateEmpAttenDetailsByAdmin";
    public static final String URL_UPDATESTUDENTLETTERGENSTATUS = BASE_URL + "user/updateStudentLetterGenStatus";

    public static final String URL_CHECKEMPLOYEELOGINATTENDANCE = BASE_URL + "user/checkEmployeeLoginAttendance";
    public static final String URL_CHECKEMPLOYEELOGOUTATTENDANCE = BASE_URL + "user/checkEmployeeLogoutAttendance";
    public static final String URL_APPLYCURRENTDAYLEAVE = BASE_URL + "user/applyCurrentDayLeave";
    public static final String URL_APPLYLEAVEFORFUTURE = BASE_URL + "user/applyLeaveForFuture";
    public static final String URL_CHECKAVAILABLELEAVE = BASE_URL + "user/checkAvailableLeave";
    public static final String URL_GETALLEMPLOYEELEAVEBYUSERID = BASE_URL + "user/getallEmployeeLeaveByUserID";
    public static final String URL_GETALLEMPLOYEELEAVEBYADMIN = BASE_URL + "user/getallEmployeeLeaveByAdmin";
    public static final String URL_DELETEEMPLOYEELEAVE = BASE_URL + "user/deleteEmployeeLeave";
    public static final String URL_UPDATEEMPLEAVESTATUS = BASE_URL + "user/updateEmpLeaveStatus";
    public static final String URL_CHECKEMPLOYEEPENDINGTASK = BASE_URL + "user/checkEmployeePendingTask";
    public static final String URL_GETALLREQUESTPENDDINGTICKETUSERNAME = BASE_URL + "user/getallRequestPenddingTicketUserName";
    public static final String URL_UPDATEREQUESTTICKETPRI = BASE_URL + "user/updateRequestTicketPri";
    public static final String URL_UPDATEREQUESTTICKETTASK = BASE_URL + "user/updateRequestTicketTask";
    public static final String URL_UPDATEREQUESTTICKETREASSIGN = BASE_URL + "user/updateRequestTicketReAssign";
    public static final String URL_UPDATEREQUESTTICKETDELETESTATUS = BASE_URL + "user/updateRequestTicketDeleteStatus";
    public static final String URL_UPDATEREQUESTTICKETTONEWUSER = BASE_URL + "user/updateRequestTicketToNewUser";
    public static final String URL_GETCOUNTPENDINGLEAVEUSERS = BASE_URL + "user/getCountPendingLeaveUsers";
    public static final String URL_ADDACTUALBATCHTIMINGS = BASE_URL + "user/addActualBatchTimings";
    public static final String URL_ADDEMPLOYEEATTENDANCEBYADMIN = BASE_URL + "user/addEmployeeAttendanceByAdmin";
    public static final String URL_GETVERIFYCODEFORWEB = BASE_URL + "user/getVerifyCodeForWeb";

    public static final String URL_GETAVAILABLEUSERROLES = BASE_URL + "user/getAvailableUserRoles";
    public static final String URL_GETCONDISCOUNTUSERS = BASE_URL + "user/getConDisCountUsers";

    public static final String URL_UPDATEREUSERCHANGEDETAILS = BASE_URL + "user/updateReuserChangeDetails";
    public static final String URL_GETALLBATCHESBYCOURSEID = BASE_URL + "user/getallBatchesByCourseId";

    public static final String URL_SENDBUZZNOTIFICATION = BASE_URL + "user/sendBuzzNotification";

    public static final String URL_GETALLBUZZBYUSERID = BASE_URL + "user/getallBuzzByUserId";

    public static final String URL_CHECKMYPENDINGNOTIFICATIONS = BASE_URL + "user/checkMyPendingNotifications";


    public static final String URL_CHECKBUZZCOUNT = BASE_URL + "user/checkBuzzCount";

    public static final String URL_AUTHORISATIONCOUNT = BASE_URL + "user/authorisationCount";
    public static final String URL_UPDATEBUZZSHARE = BASE_URL + "user/updateBuzzShare";

    public static final String URL_ADDEMPLOYEEATTENDACEBYADMIN = BASE_URL + "user/addEmployeeAttendaceByAdmin";

    public static final String URL_UPDATEBTACHCODE = BASE_URL + "user/updateBtachCode";

    public static final String URL_GETALLSTUDENTTRANSFERBATCHTRAINERBYPREFIX = BASE_URL + "user/getallStudentTransferBatchTrainerByPreFix";

    public static final String URL_ADDSTUDENTINBATCHTRANSFERFROMOLDBATCH = BASE_URL + "user/addStudentInBatchTransferFromOldBatch";

    public static final String URL_GETALLBUZZHISTORYBYTICKET = BASE_URL + "user/getallBuzzHistoryByTicket";

    public static final String URL_GETAVAILABLEISTIMEINRANGE = BASE_URL + "user/getAvailableIsTimeInRange";

    public static final String URL_GETSTATSBATCHSTUDENTS = BASE_URL + "user/getStatsBatchStudents";

    public static final String URL_GETALLONGOINGTRAINER = BASE_URL + "user/getallOngoingTrainer";
    public static final String URL_GETALLTRAINERONGOINGBRANCH = BASE_URL + "user/getallTrainerOngoingBranch";
    public static final String URL_GETALLTRAINERONGOINGBATCHES = BASE_URL + "user/getallTrainerOngoingBatches";
    public static final String URL_GETALLONGOINGBATCHES = BASE_URL + "user/getallOngoingBatches";
    public static final String URL_GETALLTOTALBATCHTIME = BASE_URL + "user/getallTotalBatchTime";
    public static final String URL_GETALLATTENDANCEPERC = BASE_URL + "user/getallAttendancePerc";
    public static final String URL_GETALLACTUALBATCHDETAILS = BASE_URL + "user/getallActualBatchDetails";
    public static final String URL_GETALLTODAYTASKCOMPLETEDETAILS = BASE_URL + "user/getallTodayTaskCompleteDetails";
    public static final String URL_GETSENDINGPENDINGSMSDETAILS = BASE_URL + "user/getSendingPendingSMSDetails";
    public static final String URL_GETALLFEESDETAILSINBATCHBYADMIN = BASE_URL + "user/getallFeesDetailsInBatchByAdmin";

    public static final String URL_GETALLDEPARTMENTBYPREFIX = BASE_URL + "user/getallDepartmentByPreFix";
    public static final String URL_GETALLEMPLOYEEBYID = BASE_URL + "user/getallEmployeeById";

    public static final String URL_GETTEMPLATETEXTLOCATIONID = BASE_URL + "user/getTemplateTextLocationID";

    public static final String URL_GETTEMPLATETEXTCOURSEID = BASE_URL + "user/getTemplateTextCourseID";
    public static final String GETEMPLOYEELEAVESBYID = BASE_URL + "user/getEmployeeLeavesByID";
    public static final String URL_ADDUSERCOMMENTDETAILS = BASE_URL + "user/addUserCommentDetails";
    public static final String URL_GETALLCOMMENTSDETAILSINBATCH = BASE_URL + "user/getallCommentsDetailsInBatch";
    public static final String URL_DELETEUSERCOMMENT = BASE_URL + "user/deleteusercomment";
    public static final String URL_UPDATEJOBSTATUS = BASE_URL + "user/updateJobStatus";
    public static final String URL_ADDBANKRECEIPT = BASE_URL + "user/addBankReceipt";
    public static final String URL_GETALLBANKDETAILS = BASE_URL + "user/getallBankDetails";
    public static final String URL_DELETEBANKDETAILS = BASE_URL + "user/deleteBankDetails";
    public static final String URL_GETALL_CAMPAIGN = BASE_URL + "user/getallcampaign";
    public static final String URL_GETALLCAMPAIGNSTUDENTSBYID = BASE_URL + "user/getallCampaignStudentsById";
    public static final String URL_GETENQUIRIESUSERDETAILS = BASE_URL + "user/getEnquiriesUserDetails";
    //offline API

    public static final String URL_GETALLVWDEPARTMENTS = BASE_URL + "user/getAllvwDepartments";
    // Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "AFCKS Images";
    public static String DATA_ENTERLEVEL_COURSES = "";
    public static String DATA_SPLIZATION_COURSES = "";

    public static String DATA_MOVE_FROM_LOCATION = "";
    public static ArrayList<String> VALUE = new ArrayList<String>();
    // public static final String SMS_ORIGIN = "WAVARM";
    public static final String SMS_ORIGIN = "AFCKST";
}
