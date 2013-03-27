package ru.AenSidhe.jefit.exporter.xlsx;

public class ExerciseData
{
	public ExerciseData(String s)
	{
		String[] data = s.split("x");

		_weight = Double.parseDouble(data[0]);
		_reps = Integer.parseInt(data[1]);
	}

	public double getWeight()
	{
		return _weight;
	}

	public int getReps()
	{
		return _reps;
	}

	private final double _weight;
	private final int _reps;
}
