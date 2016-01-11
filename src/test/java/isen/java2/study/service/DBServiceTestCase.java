package isen.java2.study.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import isen.java2.study.data.Person;
import isen.java2.study.data.Sex;
import isen.java2.study.data.stat.Stat;

public class DBServiceTestCase {
	private String query = "INSERT INTO `isen_project`.`person` (`lastname`, `firstname`, `sex`, `streetname`, `state`, `city`, `bloodtype`, `dateofbirth`) VALUES (?, ?,?, ?, ?, ?, ?, ?)";
	private MysqlDataSource dataSourceMock = mock(MysqlDataSource.class);
	private Connection connectionMock = mock(Connection.class);
	private PreparedStatement statementMock = mock(PreparedStatement.class);

	private int counter;

	private Answer<Object> incrementCounter = new Answer<Object>() {
		@Override
		public Object answer(InvocationOnMock invocation) throws Throwable {
			counter++;
			return null;
		}
	};

	@Before
	public void init() throws Exception {
		when(dataSourceMock.getConnection()).thenReturn(connectionMock);
		when(connectionMock.prepareStatement(any(String.class))).thenReturn(statementMock);
		when(connectionMock.createStatement()).thenReturn(statementMock);
		doAnswer(incrementCounter).when(statementMock).execute();
		doAnswer(incrementCounter).when(statementMock).executeUpdate();
		doAnswer(incrementCounter).when(statementMock).executeQuery();
	}

	@Test
	public void shouldReadProperties() throws Exception {
		DBService dbService = getService();
		MysqlDataSource dataSource = getDataSourceOfDBService(dbService);
		// THEN
		assertThat(dataSource.getServerName()).isEqualTo("serverValue");
		assertThat(dataSource.getPort()).isEqualTo(33007);
		assertThat(dataSource.getUser()).isEqualTo("userValue");
		assertThat(getPasswordValueOfDataSource(dataSource)).isEqualTo("s3cr3T");
		assertThat(dataSource.getDatabaseName()).isEqualTo("schemaValue");
	}

	@Test
	public void shouldBuildCorrectStatement() throws Exception {
		// GIVEN
		counter = 0;
		DBService dbService = getService();
		dbService = mockDataSource(dbService);
		Person person = new Person("Stark", "Anthony", Sex.MALE, "9826 La Jolla Farms Road", "San Diego", "California",
				"tony@shield.org", LocalDate.of(1965, 4, 4), "AB+");
		// WHEN
		dbService.save(person);
		// THEN
		verify(connectionMock).prepareStatement(eq(query));
		verify(statementMock).setString(eq(1), eq("Stark"));
		verify(statementMock).setString(eq(2), eq("Anthony"));
		verify(statementMock).setString(eq(3), eq("MALE"));
		verify(statementMock).setString(eq(4), eq("9826 La Jolla Farms Road"));
		verify(statementMock).setString(eq(5), eq("California"));
		verify(statementMock).setString(eq(6), eq("San Diego"));
		verify(statementMock).setString(eq(7), eq("AB+"));
		verify(statementMock).setDate(eq(8), eq(Date.valueOf("1965-04-04")));
		assertThat(counter).isGreaterThan(0);

	}

	@Test
	public void shouldProcessStats() throws Exception {
		DBService dbService = getService();
		dbService = mockDataSource(dbService);
		Stat stat = mock(Stat.class);
		when(stat.getQuery()).thenReturn("query");
		// WHEN
		dbService.executeStat(stat);
		// THEN
		verify(connectionMock).createStatement();
		verify(statementMock).executeQuery(eq("query"));
		verify(stat).getQuery();
		verify(stat).handle(any(ResultSet.class));
	}

	private DBService getService() {
		Properties properties = new Properties();
		properties.setProperty("db.server", "serverValue");
		properties.setProperty("db.port", "33007");
		properties.setProperty("db.username", "userValue");
		properties.setProperty("db.password", "s3cr3T");
		properties.setProperty("db.schema", "schemaValue");
		DBService dbService = new DBService(properties);
		return dbService;
	}

	private DBService mockDataSource(DBService dbService) throws Exception {
		Field field = dbService.getClass().getDeclaredField("dataSource");
		field.setAccessible(true);
		field.set(dbService, dataSourceMock);
		return dbService;
	}

	private MysqlDataSource getDataSourceOfDBService(DBService dbService) throws Exception {
		Field field = dbService.getClass().getDeclaredField("dataSource");
		field.setAccessible(true);
		return (MysqlDataSource) field.get(dbService);
	}

	private String getPasswordValueOfDataSource(MysqlDataSource dataSource) throws Exception {
		Field field = dataSource.getClass().getDeclaredField("password");
		field.setAccessible(true);
		return (String) field.get(dataSource);
	}

}
