package java8;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
class Project{

    private String projectCode;
    private String name ;
    private String client;
    private String buLeadName;

    public Project(String name, String projectCode, String client, String buLeadName) {
        this.name = name;
        this.projectCode = projectCode;
        this.client = client;
        this.buLeadName = buLeadName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getBuLeadName() {
        return buLeadName;
    }

    public void setBuLeadName(String buLeadName) {
        this.buLeadName = buLeadName;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectCode='" + projectCode + '\'' +
                ", name='" + name + '\'' +
                ", client='" + client + '\'' +
                ", buLeadName='" + buLeadName + '\'' +
                '}';
    }
}