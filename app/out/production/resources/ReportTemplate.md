#Analysis on {algorithm}

##Envirement specs

| Name | Value |
|------|-------|
| cpu  | {env_cpu} |
| os   | {env_os}  |
| compiler | {env_compiler} |
| java runtime version | {env_runtime} |
| java vm mapName | {env_vm_name} |
| java vm version | {env_vm_version} |
| available heap before runnin algorithm | {env_heap} |


##Map details
*map itself not visual though maybe link to .map file*
{map_info}

##{algorithm}

####Basic knowledge
* Theoretical time {al_time}
* Theoretical space {al_space}
<p>
{al_doc}
</p>

####Test statistics
* Time taken {test_time}
* Space taken max {test_space}
* Steps taken: {test_used_steps} / max steps {test_max_steps} *calculate somehow*
* weight of path {test_path_weight}
* End result //write path at the end with visited cells

Intepretation of processed map
* v = visited but not walked
* x = walked path
* s mapStartLocation node
* t mapTargetLocation node
* w wall  
  
{test_processed_map}
