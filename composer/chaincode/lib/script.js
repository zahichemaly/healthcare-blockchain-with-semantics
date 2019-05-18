'use strict';

const NS = "org.ehr.network";
const NS_CAREGIVER = NS + ".Caregiver";
const NS_PATIENT = NS + ".Patient";
const NS_PRESCRIPTION = NS + ".Prescription";

/**
 * Grant permission to caregiver
 * @param {org.ehr.network.GrantPermission} perm - The permission transaction
 * @transaction
 */
async function grantPermission(perm) {
  	const currentParticipant = getCurrentParticipant();
  	const patientId = currentParticipant.getIdentifier();
    var factory = getFactory();
    var permission = factory.newConcept(NS, "Permission");
    permission.patient_id = patientId;
    permission.permissionType = perm.permissionType;
    perm.caregiver.permissions.push(permission);
    // Get participant registry for Caregivers
    const caregiverRegistry = await getParticipantRegistry(NS_CAREGIVER);
    await caregiverRegistry.update(perm.caregiver);
}

/**
 * Deny permission to caregiver
 * @param {org.ehr.network.DenyPermission} perm - The permission transaction
 * @transaction
 */
async function denyPermission(perm) {
 	const currentParticipant = getCurrentParticipant();
  	const patientId = currentParticipant.getIdentifier();
    var caregiver = perm.caregiver;
    var permissionsList = caregiver.permissions;
    
    var index = permissionsList.some(p => {
        if (p.patient_id === patientId && p.permissionType === perm.permissionType){
            return permissionsList.indexOf(p);
        }
    })
    permissionsList.splice(index, 1);
    caregiver.permissions = permissionsList;
    const caregiverRegistry = await getParticipantRegistry(NS_CAREGIVER);
    await caregiverRegistry.update(caregiver);
}

/**
 * Give prescription to patient
 * @param {org.ehr.network.GivePrescription} tx - The prescription transaction
 * @transaction
 */
async function givePrescription(tx) {
    const patientRegistry = await getParticipantRegistry(NS_PATIENT);
    const patient = await patientRegistry.get(tx.patient.getIdentifier());
    const drug_id = tx.drug.id;

    var allergiesList = patient.allergies;
    allergiesList.some(x => {
        if (x.getIdentifier() === drug_id){
            console.log("Patient is allergic to drug");
            throw new Error("Specified patient is allergic to drug prescription");
        }
    });

    const assetRegistry = await getAssetRegistry(NS_PRESCRIPTION);
    var recordId = generateRecordId(drug_id);
    var factory = getFactory();
    var presc = factory.newResource(NS, "Prescription", recordId);

    // 1. Copy Medical Record values
    presc.startDate = tx.startDate;
    presc.endDate = tx.endDate;
    presc.description = tx.description;
    presc.patient = tx.patient;
    // 2. Copy Prescription values
    presc.dose_val = tx.dose_val;
    presc.drug = tx.drug;
    await assetRegistry.add(presc);
}

/**
 * Add allergy to patient
 * @param {org.ehr.network.AddAllergy} tx - The allergy transaction
 * @transaction
 */
async function addAllergy(tx) {
    var patient = tx.patient;
    var drug = tx.drug;
    patient.allergies.push(drug);
    const patientRegistry = await getParticipantRegistry(NS_PATIENT);
    await patientRegistry.update(patient);
}

/**
 * Remove allergy from patient
 * @param {org.ehr.network.RemoveAllergy} tx - The allergy transaction
 * @transaction
 */
async function removeAllergy(tx) {
    var patient = tx.patient;
    var drug = tx.drug;
    var allergiesList = patient.allergies;
    var index = allergiesList.map(x => {
        return x.id;
    }).indexOf(drug.id);
    allergiesList.splice(index, 1);
    const patientRegistry = await getParticipantRegistry(NS_PATIENT);
    await patientRegistry.update(patient);
}

/****
* Generates random ID
*/
function generateRecordId(name){
    var number = Math.random();
    var id =name + number;
    return id;
}
