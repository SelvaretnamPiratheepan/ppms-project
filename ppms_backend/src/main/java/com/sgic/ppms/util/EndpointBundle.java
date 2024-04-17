package com.sgic.ppms.util;

public class EndpointBundle {
	public static final String BASE_URL = "api/v1";

	public static final String CHILD = BASE_URL + "/child";
	public static final String ETHNICITY = BASE_URL + "/ethnicity";
	public static final String ENVIRONMENTAL_ALLERGY = BASE_URL + "/environmental-allergy";
	public static final String GENERAL_CONDITION = BASE_URL + "/general-condition";
	public static final String DRUG_MANAGEMENT_PLAN = BASE_URL + "/drug-management-plan";
	public static final String HAEMATOLOGY = BASE_URL + "/haematology";
	public static final String PRESENTING_COMPLAINT = BASE_URL + "/presenting-complaint";
	public static final String HISTORY_OF_PRESENTING_COMPLAINT = BASE_URL + "/history-of-presenting-complaint";
	public static final String FOOD_ALLERGY = BASE_URL + "/food-allergy";
	public static final String PLACE = BASE_URL + "/place";
	public static final String DIET_ALLERGY = BASE_URL + "/diet-allergy";
	public static final String ADMIT = BASE_URL + "/admit";
	public static final String FOOD = BASE_URL + "/food";
	public static final String DRUG = BASE_URL + "/drug";
	public static final String DOSE = BASE_URL + "/dose";
	public static final String FREQUENCY = BASE_URL + "/frequency";
	public static final String ROUTE = BASE_URL + "/route";
	public static final String FAMILY_HISTORY = BASE_URL + "/family-history";
	public static final String COMPLAINT = BASE_URL + "/complaint";
	public static final String DRUG_ALLERGY = BASE_URL + "/drug-allergy";
	public static final String DIET = BASE_URL + "/diet";
	public static final String CHILD_ENV_ALLERGY = BASE_URL + "/child-environmental-allergy";
	public static final String INFLAMMATORY = BASE_URL + "/inflammatory-marker";
	public static final String URINE_TEST = BASE_URL + "/urine-test";
	public static final String DEVELOPMENT = BASE_URL + "/development";
	public static final String ICD_CATEGORY = BASE_URL + "/icd-categories";
	public static final String CAUSE_OF_DEATH = BASE_URL + "/cause-of-death";
	public static final String RADIOLOGY_OTHER = BASE_URL + "/radiology-other";
	public static final String OTHER_TEST = BASE_URL + "/other-test";
	public static final String OTHER_TEST_M = BASE_URL + "/other-testm";
	public static final String VACCINE = BASE_URL + "/vaccine";
	public static final String EXAMINATION = BASE_URL + "/examination";
	public static final String ANTHROPOMETRY = BASE_URL + "/anthropometry";
	public static final String DISCHARGE = BASE_URL + "/discharge";
	public static final String BIOCHEMISTRY = BASE_URL + "/bio-chemistry";
	public static final String USSBRAIN = BASE_URL + "/uss-brain";
	public static final String USS_ADBO = BASE_URL + "/uss-adbo";
	public static final String DISEASE = BASE_URL + "/disease";
	public static final String CHILD_DEVELOPMENT = BASE_URL + "/child-development";
	public static final String CHILD_FOOD_ALLERGY = BASE_URL + "/child-food-allergy";
	public static final String ALLERGY_DETAIL = BASE_URL + "/allergy-detail";
	public static final String DIAGNOSIS_DETAILS = BASE_URL + "/diagnosis-detail";
	public static final String BIRTH_HISTORY = BASE_URL + "/birth-history";
	public static final String CXR = BASE_URL + "/cxr";
	public static final String CSF_FULL_REPORT = BASE_URL + "/csf-full-report";
	public static final String CT_CHEST = BASE_URL + "/ct-chest";
	public static final String POSTNATAL_HISTORY = BASE_URL + "/postnatal-history";
	public static final String LIVER_FUNCTION = BASE_URL + "/liver-function";
	public static final String DRUG_DETAILS = BASE_URL + "/drug-detail";
	public static final String PAST_HISTORY = BASE_URL + "/past_history";
	public static final String BLOOD_CULTURE = BASE_URL + "/blood-culture";
	public static final String IMMUNIZATION = BASE_URL + "/immunization";
	public static final String CT_BRAIN = BASE_URL + "/ct-brain";
	public static final String PARENT_DETAIL = BASE_URL + "/parent-detail";
	public static final String OTHER_CHILD = BASE_URL + "/other-child";

	public static final String CREATE = "/create";

	public static final String GET_ALL = "/all";
	public static final String GET_BY_ID = "/get-by-id/{id}";
	public static final String GET_BY_NAME = "/get-by-name/{name}";
	public static final String UPDATE_BY_ID = "/update-by-id/{id}";
	public static final String DELETE_BY_ID = "/delete-by-id/{id}";

	public static final String GET_BY_ADMIT_ID = "/get-by-admitId/{admitId}";
	public static final String UPDATE_BY_ADMIT_ID = "/update-by-admitId/{admitId}";
	public static final String DELETE_BY_ADMIT_ID = "/delete-by-admitId/{admitId}";

	public static final String GET_BY_CHILD_ID = "/get-by-childId/{childId}";
	public static final String UPDATE_BY_CHILD_ID = "/update-by-childId/{childId}";
	public static final String DELETE_BY_CHILD_ID = "/delete-by-childId/{childId}";

	// Specific Endpoints
	public static final String GET_BASICS = "/basics";
	public static final String UPDATE_AS_LIST = "/update";
	public static final String SEARCH = "/search/{anything}";

	public static final String GET_BY_TEST_ID = "/get-by-testId/{testId}";

	public static final String GET_BY_ALLERGY_DETAIL_ID = "/get-by-allergyId/{allergyId}";

	public static final String GET_BY_BETWEEN_DATES = "/betweenDates/{fromDate}/{endDate}";
	public static final String GET_BY_FAMILY_AND_SOCIAL_HISTORY_ID = "/get-by-familyAndSocialHistory-Id";

	// Prevent instantiation
	private EndpointBundle() {
	}
}