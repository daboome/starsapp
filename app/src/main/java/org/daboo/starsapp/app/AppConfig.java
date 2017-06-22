package org.daboo.starsapp.app;

import org.daboo.starsapp.R;

public class AppConfig {

    public static final CoreAppEnums.Environment ENVIRONMENT = CoreAppEnums.Environment.DEV;

    public static final String BASE_URL = GlobalApplication.getInstance().getString(R.string.server_address) + ":8080";

}
