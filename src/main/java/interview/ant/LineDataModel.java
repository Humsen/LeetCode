package interview.ant;

/**
 * @author 何明胜 husen@hemingsheng.cn
 * @since 2021-03-03 00:14:23
 */
public class LineDataModel implements Comparable<LineDataModel> {

    private String id;

    private String groupId;

    private float quota = Float.MAX_VALUE;

    public LineDataModel() {
    }

    public LineDataModel(String id, String groupId, float quota) {
        this.id = id;
        this.groupId = groupId;
        this.quota = quota;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public float getQuota() {
        return quota;
    }

    public void setQuota(float quota) {
        this.quota = quota;
    }

    @Override
    public int compareTo(LineDataModel o) {
        return Float.compare(quota, o.quota);
    }

    @Override
    public String toString() {
        return "LineDataModel{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", quota=" + quota +
                '}';
    }
}
