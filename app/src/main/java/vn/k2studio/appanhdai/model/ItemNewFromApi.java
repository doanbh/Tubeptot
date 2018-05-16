package vn.k2studio.appanhdai.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class ItemNewFromApi implements Parcelable {

    /**
     * new_id : 1
     * new_title : tin nhanh
     * new_time : 1498451673
     * new_content : Đồng Nai - một tỉnh thuộc Đông Nam Bộ, nước Việt Nam, có dân số đứng thứ 2 toàn miền Nam, đứng thứ 3 toàn quốc. Chứng tỏ nguồn lao động tại đây khá dồi dào. Tuy nhiên, quan trọng nhất vẫn là cơ hội việc làm tại Đồng Nai được tạo ra từ các ngành kinh tế tại tỉnh Đồng Nai có gì khởi sắc. Nói về việc làm Đồng Nai, rất may mắn, đây là một tỉnh thành có được nhiều tiềm năng kinh tế phát triển. Nằm ở vị trí cửa ngõ đi vào vùng kinh tế năng động và phát triển nhất cả nước, cũng là một trong số 3 tỉnh nằm trong tam giác kinh tế bao gồm Thành phố Hồ CHí Minh - Bình Dương - Đồng Nai. Tỉnh có hơn 32 khu công nghiệp ( Long Thành, An Phước, Nhơn Trạch, Biên Hòa,...) và nhiều cụm công nghiệp chuyên về nghề truyền thống. Do đó, ngành nghề lao động phổ thông sẽ là ngành có cơ hội lớn nhất về việc làm. Các sản phẩm từ ngành công nghiệp sẽ kéo theo nhiều ngành phát triển như kinh doanh, vận tải lái xe. Vận tải sẽ càng phát triển hơn khi tại đây có hệ thống giao thông vô cùng thuận lợi với sự bắc qua của tuyến đường huyết mạch cả nước - Quốc lộ 1A, Quốc lộ 20 và 51, có cả đường sắt Bắc Nam, nằm gần cảng Sài Gòn và sân bay Quốc Tế Tân Sơn Nhất.
     * new_list_image : ["http://static.hdonline.vn/i/resources/new/film/900x500/2017/11/08/hang-xom-toi-la-totoro.jpg?v=01","http://static.hdonline.vn/i/resources/new/film/900x500/2017/11/08/hang-xom-toi-la-totoro.jpg?v=01"]
     */

    private String new_id;
    private String new_title;
    private String new_time;
    private String new_content;
    private List<String> new_list_image;

    public String getNew_id() {
        return new_id;
    }

    public void setNew_id(String new_id) {
        this.new_id = new_id;
    }

    public String getNew_title() {
        return new_title;
    }

    public void setNew_title(String new_title) {
        this.new_title = new_title;
    }

    public String getNew_time() {
        return new_time;
    }

    public void setNew_time(String new_time) {
        this.new_time = new_time;
    }

    public String getNew_content() {
        return new_content;
    }

    public void setNew_content(String new_content) {
        this.new_content = new_content;
    }

    public List<String> getNew_list_image() {
        return new_list_image;
    }

    public void setNew_list_image(List<String> new_list_image) {
        this.new_list_image = new_list_image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.new_id);
        dest.writeString(this.new_title);
        dest.writeString(this.new_time);
        dest.writeString(this.new_content);
        dest.writeStringList(this.new_list_image);
    }

    public ItemNewFromApi() {
    }

    protected ItemNewFromApi(Parcel in) {
        this.new_id = in.readString();
        this.new_title = in.readString();
        this.new_time = in.readString();
        this.new_content = in.readString();
        this.new_list_image = in.createStringArrayList();
    }

    public static final Parcelable.Creator<ItemNewFromApi> CREATOR =
            new Parcelable.Creator<ItemNewFromApi>() {
                @Override
                public ItemNewFromApi createFromParcel(Parcel source) {
                    return new ItemNewFromApi(source);
                }

                @Override
                public ItemNewFromApi[] newArray(int size) {
                    return new ItemNewFromApi[size];
                }
            };
}
