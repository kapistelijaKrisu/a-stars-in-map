# ReportTemplate.md usage
In resources there is an analysis template for program to be used to write analysis.
During algorithm run it sets results given by it on template values that are listed below.
## Existing values
For easier navigation prefix env, map, al, test (up to bottom order) show where it is spotted. no prefix means used in more than 1 place.

* {algorithm}
* {env_cpu}
* {env_os}
* {env_compiler}
* {env_runtime}
* {env_vm_name}
* {env_vm_version}
* {env_heap}
* {map_info}
* {al_time}
* {al_space}
* {al_doc}
* {test_time}
* {test_space}
* {test_used_steps}
* {test_max_steps}
* {test_path_weight}
* {test_processed_map}

## test_processed_map
Markups for processed map that is formed at the end on {test_processed_map}

* . not visited
* v = visited but not walked
* X = walked path
* S start node
* T target node
* O start and target node if same
* # wall

## Validation
Values are passed during "SearchAlorithm.runSearch()"  in a map to "AnalysisWriter". ReportValidator validates it first. If any of listed values in ReportValidator are missing then report is not written.
