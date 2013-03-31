package ru.AenSidhe.jefit.exporter.xlsx;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Set
{
	public Set(String name, String logs, LocalDate date)
	{
		Iterator<String> iterator = Arrays.asList(logs.split(",")).iterator();

		while (iterator.hasNext())
		{
			try
			{
				_reps.add(new ExerciseData(iterator.next()));
			}
			catch (NumberFormatException e)
			{
				// we just skip bad data for now
			}
		}
		
		String toReplace = new String(new char[] { name.charAt(0) });
		
		StringBuilder sb = new StringBuilder(name.toLowerCase());
		sb.replace(0, 1, toReplace.toUpperCase());
		
		_name = sb.toString();
		_date = date;
	}

	public String getName()
	{
		return _name;
	}

	public Iterator<ExerciseData> getExercises()
	{
		return _reps.iterator();
	}

	public LocalDate getDate()
	{
		return _date;
	}

	private final ArrayList<ExerciseData> _reps= new ArrayList<ExerciseData>();

	private final String _name;

	private final LocalDate _date;
}
