package me.bl.Core;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConsoleFilter extends AbstractFilter {

    private static final boolean USE_RAW_STRING = false;

    public ConsoleFilter() {
        super(Filter.Result.DENY, Filter.Result.NEUTRAL);
    }

    @NotNull
    private Result doFilter(@Nullable String message) {
        if (message == null
                || !message.startsWith("Disconnecting com.mojang.authlib.GameProfile")
                && !message.startsWith("com.mojang.authlib.GameProfile")) {
            return onMismatch;
        }

        return onMatch;
    }

    @Override
    public Result filter(LogEvent event) {
        Message msg = event == null ? null : event.getMessage();
        String message = msg == null ? null : (USE_RAW_STRING
                ? msg.getFormat()
                : msg.getFormattedMessage());
        return doFilter(message);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        return doFilter(msg == null ? null : msg.toString());
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
        return doFilter(msg);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        String message = msg == null ? null : (USE_RAW_STRING
                ? msg.getFormat()
                : msg.getFormattedMessage());
        return doFilter(message);
    }
}
