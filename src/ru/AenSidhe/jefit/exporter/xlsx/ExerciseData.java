package ru.AenSidhe.jefit.exporter.xlsx;
import org.joda.time.*;
import java.util.*;

public class ExerciseData
{
	public ExerciseData(String name, String logs, LocalDate date)
	{
		_name = name;
		_logs = Arrays.asList(logs.split(","));
		_date = date;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public Iterator<String> getLogIterator()
	{
		return _logs.iterator();
	}
	
	private final String _name;
	
	private final List<String> _logs;
	
	private final LocalDate _date;
}
