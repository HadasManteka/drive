package team1.spring.training.modal;

public class ResponseModal {
    public String _message;

    public String get_message() {
        return _message;
    }

    public void set_message(String _message) {
        this._message = _message;
    }

    public ResponseModal(String message) {
        this._message = message;
    }
}
