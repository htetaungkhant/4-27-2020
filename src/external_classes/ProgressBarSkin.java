package external_classes;

import com.alee.api.resource.ClassResource;
import com.alee.managers.style.XmlSkin;

public class ProgressBarSkin extends XmlSkin{
		public ProgressBarSkin() {
				super(new ClassResource(ProgressBarSkin.class, "customProgressbar.xml"));
		}
}
