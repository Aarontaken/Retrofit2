package module;

/**
 * Created by Aaron Wang on 2016/11/11.
 */
public class WeatherInfo {

    /**
     * city : 北京
     * cityid : 101010100
     * temp : 10
     * WD : 东南风
     * WS : 2级
     * SD : 26%
     * WSE : 2
     * time : 10:25
     * isRadar : 1
     * Radar : JC_RADAR_AZ9010_JB
     * njd : 暂无实况
     * qy : 1012
     */

    public WeatherinfoBean weatherinfo;

    public static class WeatherinfoBean {
        public String city;
        public String cityid;
        public String temp;
        public String WD;
        public String WS;
        public String SD;
        public String WSE;
        public String time;
        public String isRadar;
        public String Radar;
        public String njd;
        public String qy;

        @Override
        public String toString() {
            return "WeatherinfoBean{" +
                    "city='" + city + '\'' +
                    ", cityid='" + cityid + '\'' +
                    ", temp='" + temp + '\'' +
                    ", WD='" + WD + '\'' +
                    ", WS='" + WS + '\'' +
                    ", SD='" + SD + '\'' +
                    ", WSE='" + WSE + '\'' +
                    ", time='" + time + '\'' +
                    ", isRadar='" + isRadar + '\'' +
                    ", Radar='" + Radar + '\'' +
                    ", njd='" + njd + '\'' +
                    ", qy='" + qy + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "weatherinfo=" + weatherinfo.toString() +
                '}';
    }
}
