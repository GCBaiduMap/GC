package gc.com.gcmapapp.bean;

import java.util.List;

/**
 * Created by jiguijun on 2017/12/11.
 */

public class MapInfo {
    private String project_id;
    private List<SecondMap> attributes;

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public List<SecondMap> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<SecondMap> attributes) {
        this.attributes = attributes;
    }

    public static class SecondMap{
        private String attribute_id;
        private List<ThirdMap> conditions;

        public String getAttribute_id() {
            return attribute_id;
        }

        public void setAttribute_id(String attribute_id) {
            this.attribute_id = attribute_id;
        }

        public List<ThirdMap> getConditions() {
            return conditions;
        }

        public void setConditions(List<ThirdMap> conditions) {
            this.conditions = conditions;
        }
    }

    public static class ThirdMap{
        private String condition_id;

        public String getCondition_id() {
            return condition_id;
        }

        public void setCondition_id(String condition_id) {
            this.condition_id = condition_id;
        }
    }

}

