package org.ehr.system;

public class ResponseMessage{

    public static String PatientNotFound(){
        return "Patient not found ";
    }

    public static String PatientExists(String patientId){
        return "Patient #" + patientId + " already exists";
    }

    public static String FunctionNotSupported(String function){
        return "Function '" + function + "' not supported";
    }

    public static String IncorrectNumberOfArguments(int expected){
        return "Incorrect number of arguments. Expected: " + expected;
    }

    public static String BadJsonParsing(){
        return "JSON object could not be parsed";
    }

    public static String PatientCreated(){
        return "Patient created";
    }

    public static String PrescriptionCreated(){
        return "Prescription created";
    }

    public static String PrescriptionAllergic(String allergy){
        return "Patient is allergic to prescribed medicine. Allergy is: " + allergy;
    }

    public static String PrescriptionSimilar(String similarDrug){
        return "Patient is already taking similar medicine " + similarDrug;
    }

    public static String PrescriptionBlockingEffect(String blockingEffectDrug){
        return "Prescription has one or more blocking effects to the prescribed medicine " + blockingEffectDrug;
    }

    public static String AllergyCreated(){
        return "Allergy has been added to patient";
    }

    public static String ChaincodeStubError(){
        return "Something went wrong with the chaincode stub";
    }

    public static String BadParameters(){
        return "Missing or bad parameter types";
    }

    public static String Found(String json){
        return json;
    }
}
