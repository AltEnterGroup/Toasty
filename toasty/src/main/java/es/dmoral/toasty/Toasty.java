package es.dmoral.toasty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This file is part of Toasty.
 * <p>
 * Toasty is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Toasty is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with Toasty.  If not, see <http://www.gnu.org/licenses/>.
 */

@SuppressLint("InflateParams")
public class Toasty {
    private final
    @ColorInt
    int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");

    private final
    @ColorInt
    int ERROR_COLOR = Color.parseColor("#D50000");
    private final
    @ColorInt
    int INFO_COLOR = Color.parseColor("#3F51B5");
    private final
    @ColorInt
    int SUCCESS_COLOR = Color.parseColor("#388E3C");
    private final
    @ColorInt
    int WARNING_COLOR = Color.parseColor("#FFA900");

    private final String TOAST_TYPEFACE = "sans-serif-condensed";


    //TODO yushourong
    private Toast mToast;
    private Context context;

    //TODO yushourong


    public Toasty(Context context) {
        this.context = context.getApplicationContext();
    }

    public
    @CheckResult
    Toast normal(@NonNull String message) {
        return normal(message, Toast.LENGTH_SHORT, null, false);
    }

    public
    @CheckResult
    Toast normal(@NonNull String message, Drawable icon) {
        return normal(message, Toast.LENGTH_SHORT, icon, true);
    }

    public
    @CheckResult
    Toast normal(@NonNull String message, int duration) {
        return normal(message, duration, null, false);
    }

    public
    @CheckResult
    Toast normal(@NonNull String message, int duration,
                 Drawable icon) {
        return normal(message, duration, icon, true);
    }

    public
    @CheckResult
    Toast normal(@NonNull String message, int duration,
                 Drawable icon, boolean withIcon) {
        return custom(message, icon, DEFAULT_TEXT_COLOR, duration, withIcon);
    }

    public
    @CheckResult
    Toast warning(@NonNull String message) {
        return warning(message, Toast.LENGTH_SHORT, true);
    }

    public
    @CheckResult
    Toast warning(@NonNull String message, int duration) {
        return warning(message, duration, true);
    }

    public
    @CheckResult
    Toast warning(@NonNull String message, int duration, boolean withIcon) {
        return custom(message, ToastyUtils.getDrawable(context, R.drawable.ic_error_outline_white_48dp),
                DEFAULT_TEXT_COLOR, WARNING_COLOR, duration, withIcon, true);
    }

    public
    @CheckResult
    Toast info(@NonNull String message) {
        return info(message, Toast.LENGTH_SHORT, true);
    }

    public
    @CheckResult
    Toast info(@NonNull String message, int duration) {
        return info(message, duration, true);
    }

    public
    @CheckResult
    Toast info(@NonNull String message, int duration, boolean withIcon) {
        return custom(message, ToastyUtils.getDrawable(context, R.drawable.ic_info_outline_white_48dp),
                DEFAULT_TEXT_COLOR, INFO_COLOR, duration, withIcon, true);
    }

    public
    @CheckResult
    Toast success(@NonNull String message) {
        return success(message, Toast.LENGTH_SHORT, true);
    }

    public
    @CheckResult
    Toast success(@NonNull String message, int duration) {
        return success(message, duration, true);
    }

    public
    @CheckResult
    Toast success(@NonNull String message, int duration, boolean withIcon) {
        return custom(message, ToastyUtils.getDrawable(context, R.drawable.ic_check_white_48dp),
                DEFAULT_TEXT_COLOR, SUCCESS_COLOR, duration, withIcon, true);
    }

    public
    @CheckResult
    Toast error(@NonNull String message) {
        return error(message, Toast.LENGTH_SHORT, true);
    }

    public
    @CheckResult
    Toast error(@NonNull String message, int duration) {
        return error(message, duration, true);
    }

    public
    @CheckResult
    Toast error(@NonNull String message, int duration, boolean withIcon) {
        return custom(message, ToastyUtils.getDrawable(context, R.drawable.ic_clear_white_48dp),
                DEFAULT_TEXT_COLOR, ERROR_COLOR, duration, withIcon, true);
    }

    public
    @CheckResult
    Toast custom(@NonNull String message, Drawable icon,
                 @ColorInt int textColor, int duration, boolean withIcon) {
        return custom(message, icon, textColor, -1, duration, withIcon, false);
    }

    public
    @CheckResult
    Toast custom(@NonNull String message, @DrawableRes int iconRes,
                 @ColorInt int textColor, @ColorInt int tintColor, int duration,
                 boolean withIcon, boolean shouldTint) {
        return custom(message, ToastyUtils.getDrawable(context, iconRes), textColor,
                tintColor, duration, withIcon, shouldTint);
    }


    public
    @CheckResult
    Toast custom(@NonNull String message, Drawable icon,
                 @ColorInt int textColor, @ColorInt int tintColor, int duration,
                 boolean withIcon, boolean shouldTint) {


        View toastLayout;
        ImageView toastIcon;
        TextView toastTextView;
        if (this.mToast == null) {
            this.mToast = new Toast(context);
            toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.toast_layout, null);
            toastIcon = (ImageView) toastLayout.findViewById(R.id.toast_icon);
            toastTextView = (TextView) toastLayout.findViewById(R.id.toast_text);
        } else {
            toastLayout = mToast.getView();
            toastIcon = (ImageView) toastLayout.findViewById(R.id.toast_icon);
            toastTextView = (TextView) toastLayout.findViewById(R.id.toast_text);
        }

        Drawable drawableFrame;

        if (shouldTint)
            drawableFrame = ToastyUtils.tint9PatchDrawableFrame(context, tintColor);
        else
            drawableFrame = ToastyUtils.getDrawable(context, R.drawable.toast_frame);
        ToastyUtils.setBackground(toastLayout, drawableFrame);

        if (withIcon) {
            if (icon == null)
                throw new IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true");
            ToastyUtils.setBackground(toastIcon, icon);
        } else
            toastIcon.setVisibility(View.GONE);

        toastTextView.setTextColor(textColor);
        toastTextView.setText(message);
        toastTextView.setTypeface(Typeface.create(TOAST_TYPEFACE, Typeface.NORMAL));

        mToast.setView(toastLayout);
        mToast.setDuration(duration);
        return mToast;
    }
}
