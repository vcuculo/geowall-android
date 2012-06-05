package mobidev.geowall;

import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

public class MapGestureDetector implements OnGestureListener {
	private MyAreaOverlay myArea;

	public MapGestureDetector(MyAreaOverlay area) {
		myArea = area;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		myArea.onTap(e);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		myArea.onDown(e);
		return true;
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		myArea.onTap(e);
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		myArea.onUp(e1);
		return true;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		myArea.onUp(e1);
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}
}