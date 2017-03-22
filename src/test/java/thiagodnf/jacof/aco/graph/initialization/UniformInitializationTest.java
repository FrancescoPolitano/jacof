package thiagodnf.jacof.aco.graph.initialization;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
import static org.mockito.Mockito.when;

import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.mscharhag.oleaster.runner.OleasterRunner;

import thiagodnf.jacof.aco.ACO;
import thiagodnf.jacof.aco.graph.initialization.UniformInitialization;
import thiagodnf.jacof.problem.Problem;

@RunWith(OleasterRunner.class)
public class UniformInitializationTest {{
	
	Problem problem = Mockito.mock(Problem.class);
	ACO aco = Mockito.mock(ACO.class);
	
	when(aco.getNumberOfAnts()).thenReturn(2);
	when(aco.getProblem()).thenReturn(problem);
	when(aco.getProblem().getCnn()).thenReturn(10.0);
	when(aco.getProblem().getNumberOfNodes()).thenReturn(5);
	
	describe("When initialize an EASInitialization", () -> {

		it("should throw an exception when null aco is passed", () -> {
			expect(() -> {
				new UniformInitialization(null);
			}).toThrow(NullPointerException.class);
		});
		
		it("should throw an minValue > maxValue", () -> {
			expect(() -> {
				new UniformInitialization(aco, 1.0, 0.0);
			}).toThrow(IllegalArgumentException.class);
		});
	});
	
	describe("When default range is used", () -> {
		
		it("should return a number between 0.0 and 1.0", () -> {
			expect(new UniformInitialization(aco).getT0()).toBeBetween(0.0, 1.0);
		});
	});
	
	describe("When specificed range is used", () -> {
		
		it("should return a number between 2.0 and 4.0", () -> {
			expect(new UniformInitialization(aco, 2.0, 4.0).getT0()).toBeBetween(2.0, 4.0);
		});
	});
	
	describe("When call toString method", () -> {

		it("should return the correct name", () -> {
			expect(new UniformInitialization(aco).toString()).toEqual("UniformInitialization [0.0:1.0]");
		});
	});
}}
