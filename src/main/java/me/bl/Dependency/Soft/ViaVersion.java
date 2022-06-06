package me.bl.Dependency.Soft;

public class ViaVersion {

    public static String check(int e) {

        if (e == 758) {
            return "1.18.2";
        }

        if (e == 757) {
            return "1.18/1.18.1";
        }

        if (e == 756) {
            return "1.17.1";
        }

        if (e == 754) {
            return "1.16.4/1.16.5";
        }

        if (e == 753) {
            return "1.16.3";
        }
        if (e == 751) {
            return "1.16.2";
        }
        if (e == 736) {
            return "1.16.1";
        }
        if (e == 735) {
            return "1.16";
        }
        if (e == 578) {
            return "1.15.2";
        }
        if (e == 575) {
            return "1.15.1";
        }
        if (e == 573) {
            return "1.15";
        }
        if (e == 498) {
            return "1.14.4";
        }
        if (e == 490) {
            return "1.14.3";
        }
        if (e == 485) {
            return "1.14.2";
        }
        if (e == 480) {
            return "1.14.1";
        }
        if (e == 477) {
            return "1.14";
        }

        if (e == 404) {
            return "1.13.2";
        }

        if (e == 401) {
            return "1.13.1";
        }

        if (e == 393) {
            return "1.13";
        }

        return "Under 1.13";
    }
}