package isen.java2.study.service;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import isen.java2.study.data.stat.Stat;

public class StatServiceTestCase {

	@Test
	public void shouldCallProcessStatsOfDBService() {
		// GIVEN
		DBService dbService = mock(DBService.class);
		StatService statService = new StatService(dbService);
		Stat stat1 = mock(Stat.class);
		Stat stat2 = mock(Stat.class);
		Stat stat3 = mock(Stat.class);
		List<Stat> stats = Arrays.asList(stat1, stat2, stat3);
		// WHEN
		statService.printStats(stats);
		// THEN
		verify(dbService, times(1)).executeStat(eq(stat1));
		verify(dbService, times(1)).executeStat(eq(stat2));
		verify(dbService, times(1)).executeStat(eq(stat3));
		verify(stat1, times(1)).getDescription();
		verify(stat2, times(1)).getDescription();
		verify(stat3, times(1)).getDescription();

	}

}
