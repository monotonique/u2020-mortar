package co.lemonlabs.mortar.example.ui.views.data;


import android.os.Parcel;
import android.os.Parcelable;

public class ExamplePopupData implements Parcelable {

    public final String title;
    public final String content;

    public ExamplePopupData(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Parcel part
    public ExamplePopupData(Parcel in) {
        String[] data = new String[2];

        in.readStringArray(data);
        this.title = data[0];
        this.content = data[1];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || ((Object)this).getClass() != o.getClass()) return false;

        ExamplePopupData that = (ExamplePopupData) o;

        return title.equals(that.title) && content.equals(that.content);
    }

    private static final int HASH_PRIME = 37;
    @Override
    public int hashCode() {
        int result = content.hashCode();
        result = HASH_PRIME * result + content.hashCode();
        return result;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(content);
    }

    @SuppressWarnings("UnusedDeclaration")
    public static final Creator<ExamplePopupData> CREATOR = new Creator<ExamplePopupData>() {
        @Override public ExamplePopupData createFromParcel(Parcel parcel) {
            return new ExamplePopupData(parcel);
        }

        @Override public ExamplePopupData[] newArray(int size) {
            return new ExamplePopupData[size];
        }
    };
}