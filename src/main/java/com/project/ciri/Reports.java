package com.project.ciri;

/**
 * Created by hp-pc on 21-01-2015.
 */
public class Reports {

//    private static final String KEY_ID = "id";
//    private static final String KEY_SENDER = "sender";
//    private static final String KEY_TIMESTAMP = "timestamp";
//    private static final String KEY_LONGITUDE = "longitude";
//    private static final String KEY_LATITUDE = "latitude";
//    private static final String KEY_CONTACT_NUMBER = "cntct_nmbr";
//    private static final String KEY_MEDIA_TYPE = "media_type";
//    private static final String KEY_PATH = "path";
//    private static final String KEY_TYPE_OF_INCIDENT = "incident_type";
//    private static final String KEY_POLICE = "police";
//    private static final String KEY_AMBULANCE = "ambulance";
//    private static final String KEY_FIRE = "fire";
//    private static final String KEY_NOTE = "note";

    int _id;
    String _sender;
    String _timestamp;
    double _longitude;
    double _latitude;
    String _cntct_nmbr;
    String _media_type;
    String _path;
    String _incident_type;
    int _police;
    int _ambulance;
    int _fire;
    String _note;

    public Reports(String _sender, String _timestamp, double _longitude, double _latitude, String _cntct_nmbr, String _media_type, String _path, String _incident_type, int _police, int _ambulance, int _fire, String _note) {

        this._sender = _sender;
        this._timestamp = _timestamp;
        this._longitude = _longitude;
        this._latitude = _latitude;
        this._cntct_nmbr = _cntct_nmbr;
        this._media_type = _media_type;
        this._path = _path;
        this._incident_type = _incident_type;
        this._police = _police;
        this._ambulance = _ambulance;
        this._fire = _fire;
        this._note = _note;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_sender() {
        return _sender;
    }

    public void set_sender(String _sender) {
        this._sender = _sender;
    }

    public String get_timestamp() {
        return _timestamp;
    }

    public void set_timestamp(String _timestamp) {
        this._timestamp = _timestamp;
    }

    public double get_longitude() {
        return _longitude;
    }

    public void set_longitude(double _longitude) {
        this._longitude = _longitude;
    }

    public double get_latitude() {
        return _latitude;
    }

    public void set_latitude(double _latitude) {
        this._latitude = _latitude;
    }

    public String get_cntct_nmbr() {
        return _cntct_nmbr;
    }

    public void set_cntct_nmbr(String _cntct_nmbr) {
        this._cntct_nmbr = _cntct_nmbr;
    }

    public String get_media_type() {
        return _media_type;
    }

    public void set_media_type(String _media_type) {
        this._media_type = _media_type;
    }

    public String get_path() {
        return _path;
    }

    public void set_path(String _path) {
        this._path = _path;
    }

    public String get_incident_type() {
        return _incident_type;
    }

    public void set_incident_type(String _incident_type) {
        this._incident_type = _incident_type;
    }

    public int get_police() {
        return _police;
    }

    public void set_police(int _police) {
        this._police = _police;
    }

    public int get_ambulance() {
        return _ambulance;
    }

    public void set_ambulance(int _ambulance) {
        this._ambulance = _ambulance;
    }

    public int get_fire() {
        return _fire;
    }

    public void set_fire(int _fire) {
        this._fire = _fire;
    }

    public String get_note() {
        return _note;
    }

    public void set_note(String _note) {
        this._note = _note;
    }
}
