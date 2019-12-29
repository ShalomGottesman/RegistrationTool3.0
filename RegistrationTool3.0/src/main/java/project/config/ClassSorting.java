package project.config;

import java.util.Arrays;
import java.util.Comparator;

public class ClassSorting {
	
	public ClassSorting() {}
	
	public Class[] sortClassArray(Class[] classes, String sortingMethod) {
		ClassComparator comparator = new ClassComparator(sortingMethod);
		Arrays.parallelSort(classes, comparator);
		return classes;
	}
	
	private class ClassComparator implements Comparator<Class>{
		private String[] compareOptions = {"CRN", "Name", "Priority"};
		private int compareType = -1;
		
		public ClassComparator(String str) {
			setCompareType(str);
		}
		
		@Override
		public int compare(Class o1, Class o2) {
			if (this.compareType == 0) {//CRN
				if (o1.getCRN().compareTo(o2.getCRN()) == 0) {//if CRNs are the same
					if (o1.getName().compareTo(o2.getName()) == 0) {//if the names are the same
						if(o1.getPriorityNumber().compareTo(o2.getPriorityNumber()) == 0) {
							return 0;
						} else {
							o1.getPriorityNumber().compareTo(o2.getPriorityNumber());
						}
					} else {
						return o1.getName().compareTo(o2.getName());
					}
				} else {
					return o1.getCRN().compareTo(o2.getCRN());
				}
				
			}
			
			if (this.compareType == 1) {//Name
				if (o1.getName().compareTo(o2.getName()) == 0) {//if the names are the same
					if (o1.getCRN().compareTo(o2.getCRN()) == 0) {
						if (o1.getPriorityNumber().compareTo(o2.getPriorityNumber()) == 0) {
							return 0;
						} else {
							return o1.getPriorityNumber().compareTo(o2.getPriorityNumber());
						}
					} else {
						return o1.getCRN().compareTo(o2.getCRN());
					}
				} else {
					return o1.getName().compareTo(o2.getName());
				}
			}
			
			if (this.compareType == 2) {//Priority
				if (o1.getPriorityNumber().compareTo(o2.getPriorityNumber()) == 0) {
					if (o1.getName().compareTo(o2.getName()) == 0) {
						if (o1.getCRN().compareTo(o2.getCRN()) == 0) {
							return 0;
						} else {
							return o1.getCRN().compareTo(o2.getCRN());
						}
					} else {
						o1.getName().compareTo(o2.getName());
					}
				} else {
					o1.getPriorityNumber().compareTo(o2.getPriorityNumber());
				}
				
			}
			return 0;
		}
		
		/**
		 * @param str the type o compare over, must be one of {"CRN", "Name", "Priority"}
		 */
		public void setCompareType(String str) {
			int index = -1;
			for (int x = 0; x < this.compareOptions.length; x++) {
				if (str.equals(this.compareOptions[x])){
					index = x;
				}
			}
			if (index == -1) {
				throw new IllegalArgumentException("compare type not found");
			}
			this.compareType = index;
		}
		
	}

}
