package thiagodnf.jacof.aco.subset.many;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
import static org.mockito.Mockito.when;

import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.mscharhag.oleaster.runner.OleasterRunner;

import thiagodnf.jacof.aco.ACO;
import thiagodnf.jacof.aco.ant.Ant;
import thiagodnf.jacof.problem.Problem;

@RunWith(OleasterRunner.class)
public class RankAntTest {{


	describe("When initialize an RankAnt", () -> {

		ACO aco = Mockito.mock(ACO.class);
		
		it("should throw an exception when null problem is passed", () -> {
			expect(() -> {
				new RankAnt(null);
			}).toThrow(NullPointerException.class);
		});
		
		it("should throw an exception rank < 0", () -> {
			expect(() -> {
				new RankAnt(aco, -1);
			}).toThrow(IllegalArgumentException.class);
		});
		
		it("should throw an exception rank > the number of ants", () -> {
			
			when(aco.getNumberOfAnts()).thenReturn(2);
			
			expect(() -> {
				new RankAnt(aco, 3);
			}).toThrow(IllegalArgumentException.class);
		});
	});
	
	describe("When get the list of ants", () -> {

		ACO aco = Mockito.mock(ACO.class);
		Problem p = Mockito.mock(Problem.class);
		
		when(aco.getNumberOfAnts()).thenReturn(4);
		when(aco.getProblem()).thenReturn(p);
		when(aco.getProblem().getNumberOfNodes()).thenReturn(5);
		
		Ant a1 = new Ant(aco);
		Ant a2 = new Ant(aco);
		Ant a3 = new Ant(aco);
		Ant a4 = new Ant(aco);
		
		a1.tourLength = 10;
		a2.tourLength = 20;
		a3.tourLength = 30;
		a4.tourLength = 40;
		
		it("should return a empty list with rank equals to 0", () -> {
			when(aco.getAnts()).thenReturn(new Ant[]{});
			AbstractManyAnts manyAnts = new RankAnt(aco, 0);
			expect(manyAnts.getSubSet().size()).toEqual(0);
		});
		
		describe("and the default parameter is used", () -> {
			
			it("should return a list with half size ", () -> {
				AbstractManyAnts manyAnts = new RankAnt(aco);
				
				when(aco.getAnts()).thenReturn(new Ant[]{a1, a2, a3, a4});
				expect(manyAnts.getSubSet().size()).toEqual(2);
				expect(manyAnts.getSubSet().get(0).tourLength).toEqual(40);
				expect(manyAnts.getSubSet().get(1).tourLength).toEqual(30);
			});
		});
		
		describe("ant rank is equals to 3", () -> {
		
			it("should return a clone of two ants", () -> {
				AbstractManyAnts manyAnts = new RankAnt(aco, 3);
				
				when(aco.getAnts()).thenReturn(new Ant[]{a1, a2, a3, a4});
				
				expect(manyAnts.getSubSet().size()).toEqual(3);
				expect(manyAnts.getSubSet().get(0).tourLength).toEqual(40);
				expect(manyAnts.getSubSet().get(1).tourLength).toEqual(30);
				expect(manyAnts.getSubSet().get(2).tourLength).toEqual(20);
				
				Ant c4 = manyAnts.getSubSet().get(0);
				Ant c3 = manyAnts.getSubSet().get(1);
				Ant c2 = manyAnts.getSubSet().get(2);
				
				c4.tourLength = 1;
				c3.tourLength = 2;
				c2.tourLength = 3;
				
				expect(a4.tourLength != c4.tourLength).toBeTrue();
				expect(a3.tourLength != c3.tourLength).toBeTrue();
				expect(a2.tourLength != c2.tourLength).toBeTrue();
			});
		});
	});
}}
