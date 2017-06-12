package sk.denis.davidek.baking.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;


/**
 * Created by denis on 12.06.2017.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingWidgetDataProvider(this);
    }

}
