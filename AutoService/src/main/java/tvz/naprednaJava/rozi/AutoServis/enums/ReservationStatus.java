package tvz.naprednaJava.rozi.AutoServis.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum ReservationStatus {

	/** The reserved. */
	RESERVED,

	/** The in progress. */
	IN_PROGRESS,

	/** The repaired. */
	REPAIRED,

	/** The billed. */
	BILLED,
	
	DELETED;

	public static List<ReservationStatus> getStatuses() {
		List<ReservationStatus> statuses = new ArrayList<>(EnumSet.allOf(ReservationStatus.class));
		return statuses;
	}

	public static List<ReservationStatus> getForReservationEdit() {
		List<ReservationStatus> statuses = new ArrayList<>();
		statuses.add(RESERVED);
		statuses.add(IN_PROGRESS);
		statuses.add(REPAIRED);
		return statuses;
	}
}
