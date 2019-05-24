package org.ehr.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ehr.system.models.Drug;
import org.ehr.system.models.Patient;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyModification;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EhrChaincode extends ChaincodeBase {
    private static final String INVOKE_CREATE_PATIENT = "create_patient";
    private static final String INVOKE_GET_PATIENT = "get_patient";
    private static final String INVOKE_GET_ALL_PATIENTS = "get_all_patients";
    private static final String INVOKE_GET_PATIENT_HISTORY = "get_patient_history";
    private static final String INVOKE_CREATE_PRESCRIPTION = "create_prescription";
    private static final String INVOKE_CREATE_ALLERGY = "create_allergy";

    private boolean listHasDifferentSizeThen(List<String> list, int expectedElementsNumber) {
        return list.size() != expectedElementsNumber;
    }

    private boolean checkString(String str) {
        if (str.trim().length() <= 0 || str == null)
            return false;
        return true;
    }

    @Override
    public Response init(ChaincodeStub chaincodeStub) {
        return newSuccessResponse();
    }

    @Override
    public Response invoke(ChaincodeStub chaincodeStub) {
        String functionName = chaincodeStub.getFunction();
        List<String> paramList = chaincodeStub.getParameters();
        switch (functionName){
            case INVOKE_CREATE_PATIENT:
                return createPatient(chaincodeStub, paramList);
            case INVOKE_CREATE_PRESCRIPTION:
                return createPrescription(chaincodeStub, paramList);
            case INVOKE_CREATE_ALLERGY:
                return createAllergy(chaincodeStub, paramList);
            case INVOKE_GET_PATIENT:
                return getPatient(chaincodeStub, paramList);
            case INVOKE_GET_ALL_PATIENTS:
                return getAllPatients(chaincodeStub, paramList);
            case INVOKE_GET_PATIENT_HISTORY:
                return getPatientHistory(chaincodeStub, paramList);
                default:
                    return newErrorResponse(ResponseMessage.FunctionNotSupported(functionName));
        }
    }

    public Response createPatient(ChaincodeStub chaincodeStub, List<String> paramList){
        if (listHasDifferentSizeThen(paramList, 4)) {
            return newErrorResponse(ResponseMessage.IncorrectNumberOfArguments(4));
        }
        String patientId = paramList.get(0);
        String firstName = paramList.get(1);
        String lastName = paramList.get(2);
        String dob = paramList.get(3);
        try{
            if(checkString(chaincodeStub.getStringState(patientId))){
                return newErrorResponse(ResponseMessage.PatientExists(patientId));
            }
            ObjectMapper objectMapper = new ObjectMapper();
            Patient patient = new Patient(patientId, firstName, lastName, dob);
            chaincodeStub.putState(patientId, objectMapper.writeValueAsBytes(patient));
            return newSuccessResponse(ResponseMessage.PatientCreated());
        }
        catch(Throwable e){
            return newErrorResponse(e.getMessage());
        }
    }

    private Response getPatient(ChaincodeStub chaincodeStub, List<String> paramList) {
        if (listHasDifferentSizeThen(paramList, 1)) {
            return newErrorResponse(ResponseMessage.IncorrectNumberOfArguments(1));
        }
        String patientId = paramList.get(0);
        try{
            String patientState = chaincodeStub.getStringState(patientId);
            if(checkString(patientState)){
                return newSuccessResponse(ResponseMessage.Found(patientState));
            }
            return newErrorResponse(ResponseMessage.PatientNotFound());
        }
        catch (Throwable e){
            return newErrorResponse(e.getMessage());
        }
    }

    private Response getAllPatients(ChaincodeStub chaincodeStub, List<String> paramList) {
        try{
            QueryResultsIterator<KeyValue> queryResultsIterator =
                    chaincodeStub.getStateByRange("0", "9999");
            return newSuccessResponse(buildJsonPatients(queryResultsIterator));
        }
        catch (Throwable e){
            return newErrorResponse(e.getMessage());
        }
    }

    private String buildJsonPatients(QueryResultsIterator<KeyValue> queryResultsIterator) {
        JSONObject patientsJSON = new JSONObject();
        queryResultsIterator.forEach(keyModification -> {
            try {
                String patientState = keyModification.getStringValue();
                JSONObject patientJSON = new JSONObject(patientState);
                patientsJSON.append("patients", patientJSON);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return patientsJSON.toString();
    }

    private Response createPrescription(ChaincodeStub chaincodeStub, List<String> paramList){
        if (listHasDifferentSizeThen(paramList, 3)) {
            return newErrorResponse(ResponseMessage.IncorrectNumberOfArguments(3));
        }
        String patientId = paramList.get(0);
        String drugName = paramList.get(1);
        String dosage = paramList.get(2);
        String patientState = chaincodeStub.getStringState(patientId);
        if(checkString(patientState)) {
            try{
                ObjectMapper objectMapper = new ObjectMapper();
                Patient patient = objectMapper.readValue(patientState, Patient.class);
                String responseMessage = SemanticsChecker.checkPatientPrescription(patient, drugName);
                if (responseMessage.equals(ResponseMessage.PrescriptionCreated())){
                    patient.addPrescription(new Drug(drugName, dosage));
                    chaincodeStub.putState(patientId, objectMapper.writeValueAsBytes(patient));
                    return newSuccessResponse(ResponseMessage.PrescriptionCreated());
                }
                return newErrorResponse(responseMessage);
            }
            catch(Throwable e){
                return newErrorResponse(e.getMessage());
            }
        }
        return newErrorResponse(ResponseMessage.PatientNotFound());
    }

    private Response createAllergy(ChaincodeStub chaincodeStub, List<String> paramList) {
        if (listHasDifferentSizeThen(paramList, 2)) {
            return newErrorResponse(ResponseMessage.IncorrectNumberOfArguments(2));
        }
        String patientId = paramList.get(0);
        String allergy = paramList.get(1);
        String patientState = chaincodeStub.getStringState(patientId);
        if(checkString(patientState)){
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Patient patient = objectMapper.readValue(patientState, Patient.class);
                patient.addAllergy(allergy);
                chaincodeStub.putState(patientId, objectMapper.writeValueAsBytes(patient));
                return newSuccessResponse(ResponseMessage.AllergyCreated());
            } catch (Throwable e) {
                return newErrorResponse(e.getMessage());
            }
        }
        return newErrorResponse(ResponseMessage.PatientNotFound());
    }

    private Response getPatientHistory(ChaincodeStub chaincodeStub, List<String> paramList) {
        if (listHasDifferentSizeThen(paramList, 1)) {
            return newErrorResponse(ResponseMessage.IncorrectNumberOfArguments(1));
        }
        String patientId = paramList.get(0);
        try{
            QueryResultsIterator<KeyModification> queryResultsIterator =
                    chaincodeStub.getHistoryForKey(patientId);
            return newSuccessResponse(buildJsonHistory(queryResultsIterator));
        }
        catch (Throwable e){
            return newErrorResponse(e.getMessage());
        }
    }

    private String buildJsonHistory(QueryResultsIterator<KeyModification> queryResultsIterator) {
        JSONArray jsonArray = new JSONArray();
        queryResultsIterator.forEach(keyModification -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("transactionId", keyModification.getTxId());
            map.put("timestamp", keyModification. getTimestamp().toString());
            map.put("value", keyModification.getStringValue());
            map.put("isDeleted", keyModification.isDeleted());
            jsonArray.put(map);
        });

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("transactions", jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(ResponseMessage.BadJsonParsing());
        }
        return jsonObject.toString();
    }

    public static void main(String [] args){
        new EhrChaincode().start(args);
    }
}
