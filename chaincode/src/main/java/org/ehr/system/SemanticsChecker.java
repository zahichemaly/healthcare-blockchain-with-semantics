package org.ehr.system;

import org.eclipse.rdf4j.query.*;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.ehr.system.models.Drug;
import org.ehr.system.models.Patient;

import java.util.ArrayList;
import java.util.List;

public class SemanticsChecker {
    private static final String PREFIX = "<http://www.semanticweb.org/healthcare/ontology#";
    private static final String REPO_URL = "MY_GRAPHDB_REPO_URL";

    private static String checkIfDrugIsAllergic(List<String> allergies, String drug) {
        if (allergies.isEmpty()) {
            return null;
        }
        List<String> activeComponents = new ArrayList<>();
        HTTPRepository repo = new HTTPRepository(REPO_URL);
        RepositoryConnection connection = repo.getConnection();
        try {
            TupleQuery query = connection.prepareTupleQuery(QueryLanguage.SPARQL, "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                    " PREFIX owl: <http://www.w3.org/2002/07/owl#> "
                    + "PREFIX : <http://www.semanticweb.org/healthcare/ontology#> "
                    + "SELECT ?allergies "
                    + "WHERE { :" + drug + " :hasReaction ?allergies }"
            );

            TupleQueryResult result = query.evaluate();
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                for (Binding binding : bindingSet) {
                    String key = binding.getName();
                    String stringValue = binding.getValue().stringValue();
                    String value = stringValue.substring(PREFIX.length() - 1, stringValue.length());
                    activeComponents.add(value);
                }
            }
            result.close();
        } finally {
            connection.close();
        }
        for (String allergy : allergies) {
            for (String component : activeComponents) {
                if (allergy.equalsIgnoreCase(component)) {
                    return allergy;
                }
            }
        }
        return null;
    }

    private static String checkIfDrugIsSimilar(List<Drug> prescribedDrugs, String drugName) {
        if (prescribedDrugs.isEmpty()){
            return null;
        }
        List<String> similarDrugs = new ArrayList<>();
        HTTPRepository repo = new HTTPRepository(REPO_URL);
        RepositoryConnection connection = repo.getConnection();
        try {
            TupleQuery query = connection.prepareTupleQuery(QueryLanguage.SPARQL, "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                    " PREFIX owl: <http://www.w3.org/2002/07/owl#> "
                    + "PREFIX : <http://www.semanticweb.org/healthcare/ontology#> "
                    + "SELECT ?mainComponent ?relatedDrugs "
                    + "WHERE { :" + drugName + " :hasActiveSubstance ?mainComponent . ?relatedDrugs :hasActiveSubstance ?mainComponent  }"
            );

            TupleQueryResult result = query.evaluate();
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                String value = bindingSet.getValue("relatedDrugs").stringValue();
                String similarDrug = value.substring(PREFIX.length()-1);
                similarDrugs.add(similarDrug);
            }
            result.close();
        } finally {
            connection.close();
        }
        for(Drug prescribedDrug : prescribedDrugs){
            for (String similarDrug : similarDrugs){
                String prescribedDrugName = prescribedDrug.getDrugName();
                if (prescribedDrugName.equalsIgnoreCase(similarDrug)){
                    return prescribedDrugName;
                }
            }
        }
        return null;
    }

    private static String checkIfBlockingEffect(List<Drug> prescribedDrugs, String drug) {
        if (prescribedDrugs.isEmpty()){
            return null;
        }
        List<String> blockingEffectDrugs = new ArrayList<>();
        HTTPRepository repo = new HTTPRepository(REPO_URL);
        RepositoryConnection connection = repo.getConnection();
        try {
            TupleQuery query = connection.prepareTupleQuery(QueryLanguage.SPARQL, "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                    " PREFIX owl: <http://www.w3.org/2002/07/owl#> "
                    + "PREFIX : <http://www.semanticweb.org/healthcare/ontology#> "
                    + "SELECT ?drug "
                    + "WHERE { :"+drug+" :cancelsTheEffect ?drug }"
            );

            TupleQueryResult result = query.evaluate();
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                String value = bindingSet.getValue("drug").stringValue();
                String blockingEffectDrug = value.substring(PREFIX.length()-1);
                blockingEffectDrugs.add(blockingEffectDrug);
            }
            result.close();
        } finally {
            connection.close();
        }
        for(Drug prescribedDrug : prescribedDrugs){
            for (String blockingEffectDrug : blockingEffectDrugs){
                String prescribedDrugName = prescribedDrug.getDrugName();
                if (prescribedDrugName.equalsIgnoreCase(blockingEffectDrug)){
                    return prescribedDrugName;
                }
            }
        }
        return null;
    }

    public static String checkPatientPrescription(Patient p, String drugName){
        if (p == null || drugName == null){
            return ResponseMessage.BadParameters();
        }
        String similarDrug = checkIfDrugIsSimilar(p.getPrescriptions(), drugName);
        if (similarDrug != null){
            return ResponseMessage.PrescriptionSimilar(similarDrug);
        }
        String allergy = checkIfDrugIsAllergic(p.getAllergies(), drugName);
        if (allergy != null){
            return ResponseMessage.PrescriptionAllergic(allergy);
        }
        String blockingEffectDrug = checkIfBlockingEffect(p.getPrescriptions(), drugName);
        if (blockingEffectDrug != null){
            return ResponseMessage.PrescriptionBlockingEffect(blockingEffectDrug);
        }
        return ResponseMessage.PrescriptionCreated();
    }
}
