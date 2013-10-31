package JPanels;

import org.json.JSONObject;
import PP_Observer.Notificavel;

public interface INossoPanel {
	public INossoPanel getNext();
	public void parsePacket(JSONObject packet);
	public void setarNotificavel(Notificavel notificavel);
}
