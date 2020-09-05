package in.afckstechnologies.mail.afckstrainer.JsonUtils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.afckstechnologies.mail.afckstrainer.Models.AccessoryDetailsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.ActualBatchDetailsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.AdminStudentsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.AttendancePercDetailsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.BankingdetailsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.BatchesForStudentsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.BookedBtachDAO;
import in.afckstechnologies.mail.afckstrainer.Models.BuzzViewDAO;
import in.afckstechnologies.mail.afckstrainer.Models.CampaignStudentsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.CenterDAO;
import in.afckstechnologies.mail.afckstrainer.Models.CommentModeDAO;
import in.afckstechnologies.mail.afckstrainer.Models.ContactCallingListDAO;
import in.afckstechnologies.mail.afckstrainer.Models.ContactEnquiriesListDAO;
import in.afckstechnologies.mail.afckstrainer.Models.DayPrefrenceDAO;
import in.afckstechnologies.mail.afckstrainer.Models.EmpBuzzHistroyDetailsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.FixedAssestsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.NewCoursesDAO;
import in.afckstechnologies.mail.afckstrainer.Models.OnGoingBatchDAO;
import in.afckstechnologies.mail.afckstrainer.Models.ReconciliationBankDAO;
import in.afckstechnologies.mail.afckstrainer.Models.ReconciliationDAO;
import in.afckstechnologies.mail.afckstrainer.Models.RequestChangeAttachListDAO;
import in.afckstechnologies.mail.afckstrainer.Models.RequestChangeListDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StCenterDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StCoursesDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StudentsAttendanceDetailsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StudentsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StudentsDiscontinueDetailsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StudentsFeedbackListDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StudentsFeesDetailsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StudentsInBatchListDAO;
import in.afckstechnologies.mail.afckstrainer.Models.TemplatesContactDAO;
import in.afckstechnologies.mail.afckstrainer.Models.TodayTaskCompleteDetailsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.TotalBatchTimeDetailsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.TrainersDAO;
import in.afckstechnologies.mail.afckstrainer.Models.UserFeedbacksDAO;
import in.afckstechnologies.mail.afckstrainer.Models.ViewAccessoriesListDAO;


/**
 * Created by admin on 2/18/2017.
 */

public class JsonHelper {

    private ArrayList<StudentsDAO> studentsDAOArrayList = new ArrayList<StudentsDAO>();
    private StudentsDAO studentsDAO;
    long serialNo = 001;
    int id = 1;

    private ArrayList<StudentsFeesDetailsDAO> studentsFeesDetailsDAOArrayList = new ArrayList<StudentsFeesDetailsDAO>();
    private StudentsFeesDetailsDAO studentsFeesDetailsDAO;
    private ArrayList<TemplatesContactDAO> templatesContactDAOArrayList = new ArrayList<TemplatesContactDAO>();
    private TemplatesContactDAO templatesContactDAO;

    private ArrayList<CenterDAO> centerDAOArrayList = new ArrayList<CenterDAO>();
    private CenterDAO centerDAO;
    private ArrayList<StudentsFeedbackListDAO> studentsFeedbackListDAOs = new ArrayList<StudentsFeedbackListDAO>();
    private StudentsFeedbackListDAO studentsFeedbackListDAO;

    private ArrayList<NewCoursesDAO> newCoursesDAOArrayList = new ArrayList<NewCoursesDAO>();
    private NewCoursesDAO newCoursesDAO;

    private ArrayList<StudentsAttendanceDetailsDAO> studentsAttendanceDetailsDAOArrayList = new ArrayList<StudentsAttendanceDetailsDAO>();
    private StudentsAttendanceDetailsDAO studentsAttendanceDetailsDAO;

    private ArrayList<StudentsDiscontinueDetailsDAO> studentsDiscontinueDetailsDAOs = new ArrayList<StudentsDiscontinueDetailsDAO>();
    private StudentsDiscontinueDetailsDAO studentsDiscontinueDetailsDAO;
    private ArrayList<RequestChangeListDAO> requestChangeListDAOs = new ArrayList<RequestChangeListDAO>();
    private RequestChangeListDAO requestChangeListDAO;
    private ArrayList<RequestChangeAttachListDAO> requestChangeAttachListDAOs = new ArrayList<RequestChangeAttachListDAO>();
    private RequestChangeAttachListDAO requestChangeAttachListDAO;

    private ArrayList<AdminStudentsDAO> adminStudentsDAOArrayList = new ArrayList<AdminStudentsDAO>();
    private AdminStudentsDAO adminStudentsDAO;
    private ArrayList<DayPrefrenceDAO> DayPrefrenceDAOArrayList = new ArrayList<DayPrefrenceDAO>();
    private DayPrefrenceDAO dayPrefrenceDAO;
    private ArrayList<StCoursesDAO> stCoursesDAOArrayList = new ArrayList<StCoursesDAO>();
    private StCoursesDAO stCoursesDAO;
    private ArrayList<StCenterDAO> stCenterDAOArrayList = new ArrayList<StCenterDAO>();
    private StCenterDAO stCenterDAO;

    private ArrayList<StudentsInBatchListDAO> studentsInBatchListDAOs = new ArrayList<StudentsInBatchListDAO>();
    private StudentsInBatchListDAO studentsInBatchListDAO;

    private ArrayList<BookedBtachDAO> bookedBtachDAOArrayList = new ArrayList<BookedBtachDAO>();
    private BookedBtachDAO bookedBtachDAO;
    private ArrayList<UserFeedbacksDAO> userFeedbacksDAOArrayList = new ArrayList<UserFeedbacksDAO>();
    private UserFeedbacksDAO userFeedbacksDAO;
    private ArrayList<ContactCallingListDAO> contactCallingListDAOs = new ArrayList<ContactCallingListDAO>();
    private ContactCallingListDAO contactListDAO;
    private ArrayList<ReconciliationBankDAO> reconciliationBankDAOArrayList = new ArrayList<ReconciliationBankDAO>();
    private ReconciliationBankDAO reconciliationBankDAO;
    private ArrayList<ReconciliationDAO> reconciliationDAOArrayList = new ArrayList<ReconciliationDAO>();
    private ReconciliationDAO reconciliationDAO;

    private ArrayList<ViewAccessoriesListDAO> viewAccessoriesListDAOs = new ArrayList<ViewAccessoriesListDAO>();
    private ViewAccessoriesListDAO viewAccessoriesListDAO;

    private ArrayList<AccessoryDetailsDAO> accessoryDetailsDAOArrayList = new ArrayList<AccessoryDetailsDAO>();
    private AccessoryDetailsDAO accessoryDetailsDAO;
    private ArrayList<FixedAssestsDAO> fixedAssestsDAOArrayList = new ArrayList<FixedAssestsDAO>();
    private FixedAssestsDAO fixedAssestsDAO;



    private ArrayList<BuzzViewDAO> buzzViewDAOArrayList = new ArrayList<BuzzViewDAO>();
    private BuzzViewDAO buzzViewDAO;

    private ArrayList<BatchesForStudentsDAO> batchDAOArrayList = new ArrayList<BatchesForStudentsDAO>();
    private BatchesForStudentsDAO batchDAO;


    private ArrayList<OnGoingBatchDAO> onGoingBatchDAOArrayList = new ArrayList<OnGoingBatchDAO>();
    private OnGoingBatchDAO onGoingBatchDAO;

    private ArrayList<EmpBuzzHistroyDetailsDAO> empBuzzHistroyDetailsDAOS = new ArrayList<EmpBuzzHistroyDetailsDAO>();
    private EmpBuzzHistroyDetailsDAO empBuzzHistroyDetailsDAO;

    private ArrayList<TotalBatchTimeDetailsDAO> totalBatchTimeDetailsDAOS = new ArrayList<TotalBatchTimeDetailsDAO>();
    private TotalBatchTimeDetailsDAO totalBatchTimeDetailsDAO;

    private ArrayList<AttendancePercDetailsDAO> attendancePercDetailsDAOS = new ArrayList<AttendancePercDetailsDAO>();
    private AttendancePercDetailsDAO attendancePercDetailsDAO;

    private ArrayList<ActualBatchDetailsDAO> actualBatchDetailsDAOS = new ArrayList<ActualBatchDetailsDAO>();
    private ActualBatchDetailsDAO actualBatchDetailsDAO;

    private ArrayList<TodayTaskCompleteDetailsDAO> todayTaskCompleteDetailsDAOS = new ArrayList<TodayTaskCompleteDetailsDAO>();
    private TodayTaskCompleteDetailsDAO todayTaskCompleteDetailsDAO;

    private ArrayList<TrainersDAO> trainersDAOArrayList = new ArrayList<TrainersDAO>();
    private TrainersDAO trainersDAO;

    private ArrayList<CommentModeDAO> commentModeDAOArrayList = new ArrayList<CommentModeDAO>();
    private CommentModeDAO commentModeDAO;

    private ArrayList<BankingdetailsDAO> bankingdetailsDAOArrayList = new ArrayList<BankingdetailsDAO>();
    private BankingdetailsDAO bankingdetailsDAO;

    private ArrayList<CampaignStudentsDAO> campaignStudentsDAOArrayList = new ArrayList<CampaignStudentsDAO>();
    private CampaignStudentsDAO campaignStudentsDAO;

    private ArrayList<ContactEnquiriesListDAO> contactEnquiriesListDAOArrayList = new ArrayList<ContactEnquiriesListDAO>();
    private ContactEnquiriesListDAO enquiriesListDAO;


    //studentPaser
    public ArrayList<AdminStudentsDAO> parseAdminStudentList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                adminStudentsDAO = new AdminStudentsDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                adminStudentsDAO.setBranch_id(json_data.getString("branch_id"));
                adminStudentsDAO.setCourse_id(json_data.getString("course_id"));
                adminStudentsDAO.setEmail_id(json_data.getString("email_id"));
                adminStudentsDAO.setGender(json_data.getString("gender"));
                adminStudentsDAO.setStudent_Name(json_data.getString("Student_Name"));
                adminStudentsDAO.setUser_id(json_data.getString("user_id"));
                adminStudentsDAO.setMobile_no(json_data.getString("mobile_no"));
                adminStudentsDAO.setFirst_name(json_data.getString("first_name"));
                adminStudentsDAO.setSourse(json_data.getString("Source"));
                adminStudentsDAO.setNotes(json_data.getString("Notes"));
                adminStudentsDAO.setJob_program_status(json_data.getString("job_program_status"));
                adminStudentsDAO.setNumbers("" + sequence);
                adminStudentsDAOArrayList.add(adminStudentsDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return adminStudentsDAOArrayList;
    }
    public ArrayList<CampaignStudentsDAO> parseCampaignStudentList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                campaignStudentsDAO = new CampaignStudentsDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                campaignStudentsDAO.setCampaign_id(json_data.getString("campaign_id"));
                campaignStudentsDAO.setEmail_id(json_data.getString("email_id"));
                campaignStudentsDAO.setGender(json_data.getString("gender"));
                campaignStudentsDAO.setStudent_Name(json_data.getString("Student_Name"));
                campaignStudentsDAO.setUser_id(json_data.getString("user_id"));
                campaignStudentsDAO.setMobile_no(json_data.getString("mobile_no"));
                campaignStudentsDAO.setFirst_name(json_data.getString("first_name"));
                campaignStudentsDAO.setSourse(json_data.getString("Source"));
                campaignStudentsDAO.setNotes(json_data.getString("Notes"));
                campaignStudentsDAO.setStarred(json_data.getString("starred"));
                campaignStudentsDAO.setNumbers("" + sequence);
                campaignStudentsDAOArrayList.add(campaignStudentsDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return campaignStudentsDAOArrayList;
    }
    //studentPaser
    public ArrayList<StudentsDAO> parseStudentList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                studentsDAO = new StudentsDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                studentsDAO.setBatchid(json_data.getString("batchid"));
                studentsDAO.setSbd_id(json_data.getString("sbd_id"));
                studentsDAO.setEmail_id(json_data.getString("email_id"));
                studentsDAO.setStudents_Name(json_data.getString("Students_Name"));
                studentsDAO.setFirst_name(json_data.getString("first_name"));
                studentsDAO.setUser_id(json_data.getString("id"));
                studentsDAO.setMobile_no(json_data.getString("mobile_no"));
                studentsDAO.setTotalFees(json_data.getString("TotalFees"));
                studentsDAO.setGender(json_data.getString("gender"));
                studentsDAO.setCorporate(json_data.getString("student_batch_cat"));
                studentsDAO.setNotes_id(json_data.getString("notes_id"));
                studentsDAO.setDiscontinued(json_data.getString("Discontinued"));
                studentsDAO.setStatus(json_data.getString("Status"));
                studentsDAO.setNotes(json_data.getString("Notes"));
                studentsDAO.setPrevious_attendance(json_data.getString("previous_attendance"));
                studentsDAO.setDue_amount(json_data.getString("due_amount"));
                studentsDAO.setNumbers("" + sequence);
                studentsDAOArrayList.add(studentsDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return studentsDAOArrayList;
    }

    //studentPaser
    public ArrayList<StudentsDAO> parseShowStudentList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                studentsDAO = new StudentsDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                studentsDAO.setBranch_id(json_data.getString("branch_id"));
                studentsDAO.setCourse_id(json_data.getString("course_id"));
                studentsDAO.setEmail_id(json_data.getString("email_id"));
                studentsDAO.setGender(json_data.getString("gender"));
                studentsDAO.setStudents_Name(json_data.getString("Student_Name"));
                studentsDAO.setUser_id(json_data.getString("user_id"));
                studentsDAO.setMobile_no(json_data.getString("mobile_no"));
                studentsDAO.setFirst_name(json_data.getString("first_name"));
                studentsDAO.setSourse(json_data.getString("Source"));
                studentsDAO.setNotes(json_data.getString("Notes"));
                studentsDAO.setNumbers("" + sequence);
                studentsDAOArrayList.add(studentsDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return studentsDAOArrayList;
    }

    //templatePaser
    public ArrayList<TemplatesContactDAO> parseTemplateList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                templatesContactDAO = new TemplatesContactDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                templatesContactDAO.setID(json_data.getString("ID"));
                templatesContactDAO.setSubject(json_data.getString("Subject"));
                templatesContactDAO.setTag(json_data.getString("tag"));
                templatesContactDAO.setTemplate_Text(json_data.getString("Template_Text"));
                templatesContactDAOArrayList.add(templatesContactDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return templatesContactDAOArrayList;
    }

    //studentlistfeesdetailsrPaser
    public ArrayList<StudentsFeesDetailsDAO> parseStudentFessDetailsList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                studentsFeesDetailsDAO = new StudentsFeesDetailsDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                studentsFeesDetailsDAO.setDateTimeOfEntry(json_data.getString("DateTimeOfEntry"));
                studentsFeesDetailsDAO.setFeeMode(json_data.getString("FeeMode"));
                studentsFeesDetailsDAO.setNote(json_data.getString("Note"));
                studentsFeesDetailsDAO.setReceivedBy(json_data.getString("ReceivedBy"));
                studentsFeesDetailsDAO.setUserName(json_data.getString("UserName"));
                studentsFeesDetailsDAO.setFees(json_data.getString("Fees"));
                studentsFeesDetailsDAO.setFirst_name(json_data.getString("first_name"));
                studentsFeesDetailsDAO.setLast_name(json_data.getString("last_name"));
                studentsFeesDetailsDAO.setEmail_id(json_data.getString("email_id"));
                studentsFeesDetailsDAO.setMobileNo(json_data.getString("MobileNo"));
                studentsFeesDetailsDAOArrayList.add(studentsFeesDetailsDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return studentsFeesDetailsDAOArrayList;
    }

    //studentlistattendancedetailsrPaser
    public ArrayList<StudentsAttendanceDetailsDAO> parseStudentAttendanceDetailsList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);
            int len = leadJsonObj.length();
            for (int i = 0; i < leadJsonObj.length(); i++) {
                String sequence = String.format("%03d", len--);
                studentsAttendanceDetailsDAO = new StudentsAttendanceDetailsDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                studentsAttendanceDetailsDAO.setBatch_id(json_data.getString("batch_id"));
                studentsAttendanceDetailsDAO.setStudent_name(json_data.getString("student_name"));
                studentsAttendanceDetailsDAO.setAttendance(json_data.getString("attendance"));
                studentsAttendanceDetailsDAO.setAttendanceDate(json_data.getString("AttendanceDate"));
                studentsAttendanceDetailsDAO.setNumbers("" + sequence);
                studentsAttendanceDetailsDAOArrayList.add(studentsAttendanceDetailsDAO);

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return studentsAttendanceDetailsDAOArrayList;
    }


    //centerPaser
    public ArrayList<CenterDAO> parseCenterList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                centerDAO = new CenterDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                centerDAO.setId(json_data.getString("id"));
                centerDAO.setBranch_name(json_data.getString("branch_name"));
                centerDAO.setAddress(json_data.getString("address"));
                centerDAO.setIsselected(json_data.getString("isselected"));
                centerDAOArrayList.add(centerDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return centerDAOArrayList;
    }

    //newcoursesPaser
    public ArrayList<NewCoursesDAO> parseNewCoursesList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                newCoursesDAO = new NewCoursesDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                newCoursesDAO.setId(json_data.getString("id"));
                newCoursesDAO.setCourse_type_id(json_data.getString("course_type_id"));
                newCoursesDAO.setCourse_code(json_data.getString("course_code"));
                newCoursesDAO.setCourse_name(json_data.getString("course_name"));
                newCoursesDAO.setTime_duration(json_data.getString("time_duration"));
                newCoursesDAO.setPrerequisite(json_data.getString("prerequisite"));
                newCoursesDAO.setRecommonded(json_data.getString("recommonded"));
                newCoursesDAO.setFees(json_data.getString("fees"));
                newCoursesDAO.setIsselected(json_data.getString("isselected"));
                newCoursesDAOArrayList.add(newCoursesDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newCoursesDAOArrayList;
    }

    //studentfeedbacklistfeesdetailsrPaser
    public ArrayList<StudentsFeedbackListDAO> parseStudentFeedbackDetailsList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                String sequence = String.format("%03d", i + 1);
                studentsFeedbackListDAO = new StudentsFeedbackListDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                studentsFeedbackListDAO.setId(json_data.getString("id"));
                studentsFeedbackListDAO.setBatchID(json_data.getString("BatchID"));
                studentsFeedbackListDAO.setFirst_name(json_data.getString("first_name"));
                studentsFeedbackListDAO.setLast_name(json_data.getString("last_name"));
                studentsFeedbackListDAO.setMobile_no(json_data.getString("mobile_no"));
                studentsFeedbackListDAO.setFeedback(json_data.getString("Feedback"));
                studentsFeedbackListDAO.setFeedback_date(json_data.getString("feedback_date"));
                studentsFeedbackListDAO.setEmail_id(json_data.getString("email_id"));
                studentsFeedbackListDAO.setQ1(json_data.getString("Q1"));
                studentsFeedbackListDAO.setQ2(json_data.getString("Q2"));
                studentsFeedbackListDAO.setQ3(json_data.getString("Q3"));
                studentsFeedbackListDAO.setNumbers("" + sequence);
                studentsFeedbackListDAOs.add(studentsFeedbackListDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return studentsFeedbackListDAOs;
    }

    //studentlistdiscontinuedetailsrPaser
    public ArrayList<StudentsDiscontinueDetailsDAO> parseStudentDiscontinueDetailsList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                studentsDiscontinueDetailsDAO = new StudentsDiscontinueDetailsDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                studentsDiscontinueDetailsDAO.setBatch_id(json_data.getString("BatchID"));
                if (!json_data.getString("discontinue_date").equals("null")) {
                    studentsDiscontinueDetailsDAO.setDiscontinue_date(json_data.getString("discontinue_date"));
                }
                if (!json_data.getString("discontinue_reason").equals("null")) {
                    studentsDiscontinueDetailsDAO.setDiscontinue_reason(json_data.getString("discontinue_reason"));
                }
                studentsDiscontinueDetailsDAOs.add(studentsDiscontinueDetailsDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return studentsDiscontinueDetailsDAOs;
    }

    //requestChangePaser
    public ArrayList<RequestChangeListDAO> parseRequestChangeList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                requestChangeListDAO = new RequestChangeListDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                requestChangeListDAO.setId(json_data.getString("id"));
                requestChangeListDAO.setUser_id(json_data.getString("user_id"));
                requestChangeListDAO.setAssign_to_user_id(json_data.getString("assign_to_user_id"));
                requestChangeListDAO.setAssign_user_name(json_data.getString("assign_user_name"));
                requestChangeListDAO.setCreate_user_name(json_data.getString("create_user_name"));
                requestChangeListDAO.setDate_time(json_data.getString("date_time"));
                requestChangeListDAO.setRequest_body(json_data.getString("request_body"));
                requestChangeListDAO.setRequest_subject(json_data.getString("request_subject"));
                requestChangeListDAO.setStatus(json_data.getString("status"));
                requestChangeListDAO.setTicket_close_date(json_data.getString("ticket_close_date"));
                requestChangeListDAO.setTicket_comments(json_data.getString("ticket_comments"));
                requestChangeListDAO.setCreate_user_mobile_no(json_data.getString("create_user_mobile_no"));
                requestChangeListDAO.setAssign_user_mobile_no(json_data.getString("assign_user_mobile_no"));
                requestChangeListDAO.setTicket_priority_status(json_data.getString("ticket_priority_status"));
                requestChangeListDAO.setUser_name(json_data.getString("user_name"));
                requestChangeListDAO.setAttachment_count(json_data.getString("attachment_count"));
                requestChangeListDAO.setPending_days(json_data.getString("pending_days"));
                requestChangeListDAO.setExpected_date(json_data.getString("expected_date"));
                requestChangeListDAO.setExpected_time(json_data.getString("expected_time"));
                requestChangeListDAO.setBuzz_count(json_data.getString("buzz_count"));
                requestChangeListDAO.setRead_status(json_data.getString("read_status"));
                requestChangeListDAO.setDepartment_id(json_data.getString("department_id"));
                requestChangeListDAO.setNumbers("" + sequence);
                requestChangeListDAOs.add(requestChangeListDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return requestChangeListDAOs;
    }

    //requestChangePaser
    public ArrayList<RequestChangeAttachListDAO> parseRequestChangeAttachList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                requestChangeAttachListDAO = new RequestChangeAttachListDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                requestChangeAttachListDAO.setId(json_data.getString("id"));
                requestChangeAttachListDAO.setPath(json_data.getString("path"));
                requestChangeAttachListDAO.setAssign_to_user_id(json_data.getString("assign_to_user_id"));
                requestChangeAttachListDAO.setReqest_change_id(json_data.getString("reqest_change_id"));


                // requestChangeListDAO.setNumbers(""+sequence);
                requestChangeAttachListDAOs.add(requestChangeAttachListDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return requestChangeAttachListDAOs;
    }

    //centerPaser
    public ArrayList<DayPrefrenceDAO> parseDayPrefrenceList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                dayPrefrenceDAO = new DayPrefrenceDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                dayPrefrenceDAO.setId(json_data.getString("id"));
                dayPrefrenceDAO.setPrefrence(json_data.getString("Prefrence"));
                dayPrefrenceDAO.setIsselected(json_data.getString("isselected"));
                DayPrefrenceDAOArrayList.add(dayPrefrenceDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return DayPrefrenceDAOArrayList;
    }

    //stcoursesPaser
    public ArrayList<StCoursesDAO> parseStCoursesList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                stCoursesDAO = new StCoursesDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                stCoursesDAO.setId(json_data.getString("id"));
                stCoursesDAO.setType_name(json_data.getString("type_name"));
                stCoursesDAO.setCourse_code(json_data.getString("course_code"));
                stCoursesDAO.setCourse_name(json_data.getString("course_name"));
                stCoursesDAO.setType_name_id(json_data.getString("type_name_id"));
                stCoursesDAO.setNumbers("" + sequence);
                stCoursesDAOArrayList.add(stCoursesDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return stCoursesDAOArrayList;
    }

    //stcenterPaser
    public ArrayList<StCenterDAO> parseStCenterList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                stCenterDAO = new StCenterDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                stCenterDAO.setId(json_data.getString("id"));
                stCenterDAO.setBranch_name(json_data.getString("branch_name"));
                stCenterDAOArrayList.add(stCenterDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return stCenterDAOArrayList;
    }

    //batchstudentPaser
    public ArrayList<StudentsInBatchListDAO> parseBatchStudentList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                studentsInBatchListDAO = new StudentsInBatchListDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                studentsInBatchListDAO.setEmail_id(json_data.getString("email_id"));
                studentsInBatchListDAO.setGender(json_data.getString("gender"));
                studentsInBatchListDAO.setStudent_Name(json_data.getString("Students_Name"));
                studentsInBatchListDAO.setMobile_no(json_data.getString("mobile_no"));
                studentsInBatchListDAO.setFirst_name(json_data.getString("first_name"));
                studentsInBatchListDAO.setBaseFees(json_data.getString("BaseFees"));
                studentsInBatchListDAO.setCourse_name(json_data.getString("course_name"));
                studentsInBatchListDAO.setFees(json_data.getString("fees"));
                studentsInBatchListDAO.setStart_date(json_data.getString("start_date"));
                studentsInBatchListDAO.setBatch_code(json_data.getString("batch_code"));
                studentsInBatchListDAO.setStatus(json_data.getString("Status"));
                studentsInBatchListDAO.setPrevious_attendance(json_data.getString("previous_attendance"));
                studentsInBatchListDAO.setDiscontinue_reason(json_data.getString("discontinue_reason"));
                studentsInBatchListDAOs.add(studentsInBatchListDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return studentsInBatchListDAOs;
    }

    //bookedbatchPaser
    public ArrayList<BookedBtachDAO> parseBookedBatchList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                bookedBtachDAO = new BookedBtachDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                bookedBtachDAO.setBatch_code(json_data.getString("batch_code"));
                bookedBtachDAO.setStatus(json_data.getString("status"));
                bookedBtachDAO.setUser_id(json_data.getString("user_id"));
                bookedBtachDAO.setBook_id(json_data.getString("book_id"));
                bookedBtachDAO.setStudents_Name(json_data.getString("Students_Name"));
                bookedBtachDAO.setNumbers("" + sequence);
                bookedBtachDAOArrayList.add(bookedBtachDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bookedBtachDAOArrayList;
    }

    //userfeedback approval Paser
    public ArrayList<UserFeedbacksDAO> parseUserFeedbacksList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                userFeedbacksDAO = new UserFeedbacksDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                userFeedbacksDAO.setId(json_data.getString("id"));
                userFeedbacksDAO.setHeader(json_data.getString("Header"));
                userFeedbacksDAO.setEmail_id(json_data.getString("email_id"));
                userFeedbacksDAO.setFeedback(json_data.getString("feedback"));
                userFeedbacksDAO.setStudent_name(json_data.getString("student_name"));
                userFeedbacksDAO.setUser_id(json_data.getString("user_id"));
                userFeedbacksDAO.setMobile_no(json_data.getString("mobile_no"));
                userFeedbacksDAO.setFeedbackDate(json_data.getString("FeedbackDate"));
                userFeedbacksDAO.setFooter(json_data.getString("Footer"));
                userFeedbacksDAO.setNumbers("" + sequence);
                userFeedbacksDAOArrayList.add(userFeedbacksDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return userFeedbacksDAOArrayList;
    }

    //callbackstudentPaser
    public ArrayList<ContactCallingListDAO> parsecallUserList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                contactListDAO = new ContactCallingListDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                contactListDAO.setId(json_data.getString("id"));
                contactListDAO.setMobile_no(json_data.getString("mobile_no"));
                contactListDAO.setDate(json_data.getString("created_at"));
                contactListDAO.setStatus(json_data.getString("status"));
                contactListDAO.setEmail_id(json_data.getString("email_id"));
                contactListDAO.setFirst_name(json_data.getString("first_name"));
                contactListDAO.setLast_name(json_data.getString("last_name"));
                contactListDAO.setStart_date(json_data.getString("start_date"));
                contactListDAO.setEnd_date(json_data.getString("end_date"));
                contactListDAO.setNotes(json_data.getString("notes"));
                contactCallingListDAOs.add(contactListDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return contactCallingListDAOs;
    }

    //callbackstudentPaser
    public ArrayList<ReconciliationBankDAO> parseReconciliationBankList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                reconciliationBankDAO = new ReconciliationBankDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                reconciliationBankDAO.setAmount(json_data.getString("amount"));
                reconciliationBankDAO.setBank_id(json_data.getString("bank_id"));
                reconciliationBankDAO.setNarration(json_data.getString("narration"));
                reconciliationBankDAO.setStatus(json_data.getString("status"));
                reconciliationBankDAO.setT_date(json_data.getString("t_date"));
                reconciliationBankDAO.setUnique_id(json_data.getString("unique_id"));

                reconciliationBankDAOArrayList.add(reconciliationBankDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reconciliationBankDAOArrayList;
    }

    //callbackstudentPaser
    public ArrayList<ReconciliationDAO> parseReconciliationStudentList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                reconciliationDAO = new ReconciliationDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                reconciliationDAO.setUser_id(json_data.getString("user_id"));
                reconciliationDAO.setStudent_Name(json_data.getString("Student_Name"));
                reconciliationDAO.setId(json_data.getString("id"));
                reconciliationDAO.setMobileNo(json_data.getString("MobileNo"));
                reconciliationDAO.setBatchNo(json_data.getString("BatchNo"));
                reconciliationDAO.setDateTimeOfEntry(json_data.getString("DateTimeOfEntry"));
                reconciliationDAO.setFeeMode("" + sequence);
                reconciliationDAO.setFees(json_data.getString("Fees"));
                reconciliationDAO.setReceivedBy(json_data.getString("ReceivedBy"));
                reconciliationDAO.setPaymentStatus(json_data.getString("PaymentStatus"));
                reconciliationDAOArrayList.add(reconciliationDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reconciliationDAOArrayList;
    }

    //ViewAccessoriesPaser
    public ArrayList<ViewAccessoriesListDAO> parseViewAccessoriesList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                viewAccessoriesListDAO = new ViewAccessoriesListDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                viewAccessoriesListDAO.setFixed_assets_id(json_data.getString("fixed_assets_id"));
                viewAccessoriesListDAO.setAccessoryQty(json_data.getString("AccessoryQty"));
                viewAccessoriesListDAO.setAccessory_id(json_data.getString("accessory_id"));
                viewAccessoriesListDAO.setBrand_id(json_data.getString("brand_id"));
                viewAccessoriesListDAO.setAccessory_name(json_data.getString("accessory_name"));
                viewAccessoriesListDAO.setBrand_name(json_data.getString("brand_name"));
                viewAccessoriesListDAO.setId(json_data.getString("id"));
                viewAccessoriesListDAO.setIdentification(json_data.getString("Identification"));
                viewAccessoriesListDAO.setIdentification2(json_data.getString("Identification2"));
                viewAccessoriesListDAO.setIdentification3(json_data.getString("Identification3"));
                viewAccessoriesListDAO.setDateTime(json_data.getString("DateTime"));
                viewAccessoriesListDAO.setItemNameID(json_data.getString("ItemNameID"));
                viewAccessoriesListDAO.setLocationID(json_data.getString("LocationID"));
                viewAccessoriesListDAO.setQty(json_data.getString("Qty"));
                viewAccessoriesListDAO.setStatusID(json_data.getString("StatusID"));
                viewAccessoriesListDAO.setUserID(json_data.getString("UserID"));
                viewAccessoriesListDAO.setIdentificationID(json_data.getString("IdentificationID"));
                viewAccessoriesListDAO.setStockQty(json_data.getString("StockQty"));
                viewAccessoriesListDAO.setNumbers("" + sequence + "(" + json_data.getString("id") + ")");
                viewAccessoriesListDAO.setItemName(json_data.getString("ItemName"));
                viewAccessoriesListDAO.setLocation(json_data.getString("location"));
                viewAccessoriesListDAO.setStatus(json_data.getString("status"));
                viewAccessoriesListDAOs.add(viewAccessoriesListDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return viewAccessoriesListDAOs;
    }

    //stcenterPaser
    public ArrayList<AccessoryDetailsDAO> parseAccessoryList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                accessoryDetailsDAO = new AccessoryDetailsDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                accessoryDetailsDAO.setAccessory_name(json_data.getString("accessory_name"));
                accessoryDetailsDAO.setBrand_name(json_data.getString("brand_name"));
                accessoryDetailsDAO.setQty(json_data.getString("qty"));
                accessoryDetailsDAO.setAccessory_id(json_data.getString("accessory_id"));
                accessoryDetailsDAO.setBrand_id(json_data.getString("brand_id"));
                accessoryDetailsDAOArrayList.add(accessoryDetailsDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return accessoryDetailsDAOArrayList;
    }

    //fixedAssestsPaser
    public ArrayList<FixedAssestsDAO> parseFixedAssetsList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                fixedAssestsDAO = new FixedAssestsDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                fixedAssestsDAO.setId(json_data.getString("id"));
                fixedAssestsDAO.setIdentification(json_data.getString("Identification"));
                fixedAssestsDAO.setIdentification2(json_data.getString("Identification2"));
                fixedAssestsDAO.setIdentification3(json_data.getString("Identification3"));
                fixedAssestsDAO.setDateTime(json_data.getString("DateTime"));
                fixedAssestsDAO.setItemNameID(json_data.getString("ItemNameID"));
                fixedAssestsDAO.setLocationID(json_data.getString("LocationID"));
                fixedAssestsDAO.setQty(json_data.getString("Qty"));
                fixedAssestsDAO.setStatusID(json_data.getString("StatusID"));
                fixedAssestsDAO.setUserID(json_data.getString("UserID"));
                fixedAssestsDAO.setIdentificationID(json_data.getString("IdentificationID"));
                fixedAssestsDAO.setStockQty(json_data.getString("StockQty"));
                fixedAssestsDAO.setNumbers("" + sequence + "(" + json_data.getString("id") + ")");
                fixedAssestsDAO.setItemName(json_data.getString("ItemName"));
                fixedAssestsDAO.setLocation(json_data.getString("location"));
                fixedAssestsDAO.setStatus(json_data.getString("status"));
                fixedAssestsDAO.setAnti_id(json_data.getString("anti_id"));
                fixedAssestsDAO.setExpiry_date(json_data.getString("expiry_date"));
                fixedAssestsDAO.setPosition(json_data.getString("position"));
                fixedAssestsDAO.setSerial_key(json_data.getString("serial_key"));
                fixedAssestsDAOArrayList.add(fixedAssestsDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fixedAssestsDAOArrayList;
    }





    //newcoursesPaser
    public ArrayList<BatchesForStudentsDAO> parseBatchesList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                batchDAO = new BatchesForStudentsDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                batchDAO.setBatchtype(json_data.getString("batchtype"));
                batchDAO.setId(json_data.getString("id"));
                batchDAO.setStart_date(json_data.getString("new_start_date"));
                batchDAO.setTimings(json_data.getString("timings"));
                batchDAO.setFrequency(json_data.getString("frequency"));
                batchDAO.setDuration(json_data.getString("duration"));
                batchDAO.setFees(json_data.getString("fees"));
                batchDAO.setFaculty_Name(json_data.getString("faculty_Name"));
                batchDAO.setNotes(json_data.getString("Notes"));
                batchDAO.setCourse_name(json_data.getString("course_name"));
                batchDAO.setBranch_name(json_data.getString("branch_name"));
                batchDAOArrayList.add(batchDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return batchDAOArrayList;
    }

    //newcoursesPaser
    public ArrayList<BuzzViewDAO> parseRequestBuzzList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                buzzViewDAO = new BuzzViewDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                buzzViewDAO.setId(json_data.getString("id"));
                buzzViewDAO.setPath(json_data.getString("path"));
                buzzViewDAO.setAssign_to_user_id(json_data.getString("assign_to_user_id"));
                buzzViewDAO.setCreate_user_name(json_data.getString("create_user_name"));
                buzzViewDAO.setDate_time(json_data.getString("date_time"));
                buzzViewDAO.setStatus(json_data.getString("status"));
                buzzViewDAO.setTicket_cat_status(json_data.getString("ticket_cat_status"));
                buzzViewDAO.setTicket_priority_status(json_data.getString("ticket_priority_status"));
                buzzViewDAO.setRequest_subject(json_data.getString("request_subject"));
                buzzViewDAO.setRequest_body(json_data.getString("request_body"));
                buzzViewDAO.setMoney_status(json_data.getString("money_status"));
                buzzViewDAO.setTask_id(json_data.getString("task_id"));
                buzzViewDAO.setBuzz_count(json_data.getString("buzz_count"));
                buzzViewDAO.setNumbers("" + sequence);
                buzzViewDAOArrayList.add(buzzViewDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return buzzViewDAOArrayList;
    }

    //employee buzz history detailsrPaser
    public ArrayList<EmpBuzzHistroyDetailsDAO> parseEmployeeBuzzDetailsList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);
            int len = leadJsonObj.length();
            for (int i = 0; i < leadJsonObj.length(); i++) {
                String sequence = String.format("%03d", len--);
                empBuzzHistroyDetailsDAO = new EmpBuzzHistroyDetailsDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                empBuzzHistroyDetailsDAO.setId(json_data.getString("id"));
                empBuzzHistroyDetailsDAO.setTask_id(json_data.getString("task_id"));
                empBuzzHistroyDetailsDAO.setBuzz_dates(json_data.getString("buzz_dates"));
                empBuzzHistroyDetailsDAO.setRemarks(json_data.getString("remarks"));
                empBuzzHistroyDetailsDAO.setStatus(json_data.getString("status"));
                empBuzzHistroyDetailsDAO.setNumbers("" + sequence);
                empBuzzHistroyDetailsDAOS.add(empBuzzHistroyDetailsDAO);

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return empBuzzHistroyDetailsDAOS;
    }

    //fixedAssestsPaser
    public ArrayList<OnGoingBatchDAO> parseOnGoingBatchesList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                onGoingBatchDAO = new OnGoingBatchDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                onGoingBatchDAO.setBatch_code(json_data.getString("batch_code"));
                onGoingBatchDAO.setCourse_name(json_data.getString("course_name"));
                onGoingBatchDAO.setTrainer_id(json_data.getString("trainer_id"));
                onGoingBatchDAO.setSt_date(json_data.getString("st_date"));
                onGoingBatchDAO.setTimings(json_data.getString("timings"));
                onGoingBatchDAO.setFrequency(json_data.getString("frequency"));
                onGoingBatchDAO.setBranch_name(json_data.getString("branch_name"));
                onGoingBatchDAO.setTotalClasses(json_data.getString("TotalClasses"));
                onGoingBatchDAO.setTotalTime(json_data.getString("TotalTime"));
                onGoingBatchDAO.setStudentsInBatch(json_data.getString("StudentsInBatch"));
                onGoingBatchDAO.setActiveStudents(json_data.getString("ActiveStudents"));
                onGoingBatchDAO.setDiscontinuedStudents(json_data.getString("DiscontinuedStudents"));
                onGoingBatchDAO.setActivePerc(json_data.getString("ActivePerc"));
                onGoingBatchDAO.setPresentPerc(json_data.getString("PresentPerc"));
                onGoingBatchDAO.setTotalFees(json_data.getString("TotalFees"));
                onGoingBatchDAO.setMobile_no(json_data.getString("mobile_no"));
                onGoingBatchDAOArrayList.add(onGoingBatchDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return onGoingBatchDAOArrayList;
    }

    //TotalBatchTimedetailsrPaser
    public ArrayList<TotalBatchTimeDetailsDAO> parseTotalBatchTimeDetailsList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);
            int len = leadJsonObj.length();
            for (int i = 0; i < leadJsonObj.length(); i++) {
                String sequence = String.format("%03d", len--);
                totalBatchTimeDetailsDAO = new TotalBatchTimeDetailsDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                totalBatchTimeDetailsDAO.setBatch_id(json_data.getString("batch_id"));
                totalBatchTimeDetailsDAO.setBatch_date(json_data.getString("batch_date"));
                totalBatchTimeDetailsDAO.setTotalTime(json_data.getString("TotalTime"));
                totalBatchTimeDetailsDAO.setNumbers("" + sequence);
                totalBatchTimeDetailsDAOS.add(totalBatchTimeDetailsDAO);

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return totalBatchTimeDetailsDAOS;
    }

    //TotalBatchTimedetailsrPaser
    public ArrayList<AttendancePercDetailsDAO> parseAttendancePercDetailsList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);
            int len = leadJsonObj.length();
            for (int i = 0; i < leadJsonObj.length(); i++) {
                String sequence = String.format("%03d", len--);
                attendancePercDetailsDAO = new AttendancePercDetailsDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                attendancePercDetailsDAO.setBatch_id(json_data.getString("batch_id"));
                attendancePercDetailsDAO.setPresentDate(json_data.getString("PresentDate"));
                attendancePercDetailsDAO.setAttendancePerc(json_data.getString("AttendancePerc"));
                attendancePercDetailsDAO.setNumbers("" + sequence);
                attendancePercDetailsDAOS.add(attendancePercDetailsDAO);

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return attendancePercDetailsDAOS;
    }

    //TotalBatchTimedetailsrPaser
    public ArrayList<ActualBatchDetailsDAO> parseActualBatchDetailsList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);
            int len = leadJsonObj.length();
            for (int i = 0; i < leadJsonObj.length(); i++) {
                String sequence = String.format("%03d", len--);
                actualBatchDetailsDAO = new ActualBatchDetailsDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                actualBatchDetailsDAO.setId(json_data.getString("id"));
                actualBatchDetailsDAO.setBatch_id(json_data.getString("batch_id"));
                actualBatchDetailsDAO.setBatch_date(json_data.getString("batch_date"));
                actualBatchDetailsDAO.setDate_time(json_data.getString("date_time"));
                actualBatchDetailsDAO.setTodays_topics(json_data.getString("todays_topics"));
                actualBatchDetailsDAO.setNext_class_topics(json_data.getString("next_class_topics"));
                actualBatchDetailsDAO.setNext_class_date(json_data.getString("next_class_date"));
                actualBatchDetailsDAO.setNotes(json_data.getString("notes"));
                actualBatchDetailsDAO.setCourse_name(json_data.getString("course_name"));
                actualBatchDetailsDAO.setNumbers("" + sequence);
                actualBatchDetailsDAOS.add(actualBatchDetailsDAO);

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return actualBatchDetailsDAOS;
    }


    //TotalBatchTimedetailsrPaser
    public ArrayList<TodayTaskCompleteDetailsDAO> parseTodayTaskCompleteDetailsList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);
            int len = leadJsonObj.length();
            for (int i = 0; i < leadJsonObj.length(); i++) {
                String sequence = String.format("%03d", len--);
                todayTaskCompleteDetailsDAO = new TodayTaskCompleteDetailsDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                todayTaskCompleteDetailsDAO.setId(json_data.getString("id"));
                todayTaskCompleteDetailsDAO.setRequest_subject(json_data.getString("request_subject"));
                todayTaskCompleteDetailsDAO.setRequest_body(json_data.getString("request_body"));
                todayTaskCompleteDetailsDAO.setTicket_comments(json_data.getString("ticket_comments"));
                todayTaskCompleteDetailsDAO.setNumbers("" + sequence);
                todayTaskCompleteDetailsDAOS.add(todayTaskCompleteDetailsDAO);


            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return todayTaskCompleteDetailsDAOS;
    }

    //studentlistfeesdetailsrPaser
    public ArrayList<TrainersDAO> parseTrainerDetailsList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                trainersDAO = new TrainersDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                trainersDAO.setId(json_data.getString("id"));
                trainersDAO.setFirst_name(json_data.getString("first_name"));
                trainersDAO.setMobile_no(json_data.getString("mobile_no"));
                trainersDAO.setEmail_id(json_data.getString("email_id"));
                trainersDAO.setNumbers(sequence);
                trainersDAOArrayList.add(trainersDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return trainersDAOArrayList;
    }

    //studentlistfeesdetailsrPaser
    public ArrayList<CommentModeDAO> parseStudentCommentDetailsList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                commentModeDAO = new CommentModeDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                commentModeDAO.setId(json_data.getString("id"));
                commentModeDAO.setStudent_comments(json_data.getString("student_comment"));
                commentModeDAO.setDate_comments(json_data.getString("display_date"));
                commentModeDAOArrayList.add(commentModeDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return commentModeDAOArrayList;
    }

    //bankingdetailsPaser
    public ArrayList<BankingdetailsDAO> parseBankingDetailsList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                bankingdetailsDAO = new BankingdetailsDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                bankingdetailsDAO.setId(json_data.getString("id"));
                bankingdetailsDAO.setRec_amount(json_data.getString("rec_amount"));
                bankingdetailsDAO.setAdj_amount(json_data.getString("adj_amount"));
                bankingdetailsDAO.setEntered_by(json_data.getString("entered_by"));
                bankingdetailsDAO.setTrans_date(json_data.getString("trans_date"));
                bankingdetailsDAO.setTrans_ref(json_data.getString("trans_ref"));
                bankingdetailsDAO.setTrans_type(json_data.getString("trans_type"));
                bankingdetailsDAO.setEntry_date(json_data.getString("entry_date"));
                bankingdetailsDAO.setNumbers("" + sequence);
                bankingdetailsDAOArrayList.add(bankingdetailsDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bankingdetailsDAOArrayList;
    }

    //callbackstudentPaser
    public ArrayList<ContactEnquiriesListDAO> parseEnquiriesUserList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                enquiriesListDAO = new ContactEnquiriesListDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                enquiriesListDAO.setId(json_data.getString("id"));
                enquiriesListDAO.setMobile_number(json_data.getString("mobile_number"));
                enquiriesListDAO.setCaller_comments(json_data.getString("caller_comments"));
                enquiriesListDAO.setRequirement(json_data.getString("requirement"));
                enquiriesListDAO.setLooking_for(json_data.getString("looking_for"));
                enquiriesListDAO.setStarred(json_data.getString("starred"));
                enquiriesListDAO.setFull_name(json_data.getString("full_name"));
                enquiriesListDAO.setDate_of_enquiry(json_data.getString("date_of_enquiry"));
                enquiriesListDAO.setCall_status(json_data.getString("call_status"));

                contactEnquiriesListDAOArrayList.add(enquiriesListDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return contactEnquiriesListDAOArrayList;
    }

}
