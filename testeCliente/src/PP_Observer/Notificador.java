package PP_Observer;

import org.json.JSONObject;

public interface Notificador {
	public void notificar(JSONObject packet);
	public void setarNotificavel(Notificavel notificavel);
}
