package com.suyo.muezzin.model;

import com.batoulapps.adhan.CalculationMethod;
import com.batoulapps.adhan.Madhab;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrayTimesMain {
    @Schema(title = "Date AD", example = "2023-yil 18-September", description = "The date in the AD (Gregorian) calendar")
    private String dateAD;
    @Schema(title = "Date Hijri", example = "1445-yil 3-Rabi' al-Awwal", description = "The date in the Hijri calendar")
    private String dateHijri;
    @Schema(title = "Madhab", example = "HANAFI", description = "The Madhab (school of thought) used for calculations (HANAFI or SHAFI)")
    private Madhab madhab;
    @Schema(title = "Calculation Method", example = "MUSLIM_WORLD_LEAGUE", description = "The calculation method used for prayer times (MUSLIM_WORLD_LEAGUE, EGYPTIAN, KARACHI, UMM_AL_QURA, DUBAI, MOON_SIGHTING_COMMITTEE, NORTH_AMERICA, KUWAIT, QATAR, or SINGAPORE)")
    private CalculationMethod method;
    @Schema(title = "Prayer Times", description = "The prayer times for the specified date and location")
    private PrayTimes prayTimes;
    @Schema(title = "Calculation Data", description = "Additional data used for the prayer time calculations")
    private GeoIP calculationData;
}
