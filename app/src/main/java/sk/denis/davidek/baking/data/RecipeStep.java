package sk.denis.davidek.baking.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by denis on 09.06.2017.
 */

public class RecipeStep implements Parcelable {

    private String shortStepDescription;
    private String stepDescription;
    private String videoUrl;
    private String thumbnailUrl;

    public RecipeStep(String shortStepDescription, String stepDescription, String videoUrl, String thumbnailUrl) {
        this.shortStepDescription = shortStepDescription;
        this.stepDescription = stepDescription;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    protected RecipeStep(Parcel in) {
        shortStepDescription = in.readString();
        stepDescription = in.readString();
        videoUrl = in.readString();
        thumbnailUrl = in.readString();
    }

    public static final Creator<RecipeStep> CREATOR = new Creator<RecipeStep>() {
        @Override
        public RecipeStep createFromParcel(Parcel in) {
            return new RecipeStep(in);
        }

        @Override
        public RecipeStep[] newArray(int size) {
            return new RecipeStep[size];
        }
    };

    public String getShortStepDescription() {
        return shortStepDescription;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(shortStepDescription);
        parcel.writeString(stepDescription);
        parcel.writeString(videoUrl);
        parcel.writeString(thumbnailUrl);
    }
}
