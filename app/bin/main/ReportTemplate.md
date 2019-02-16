# Analysis on {algorithm}

## Envirement specs

| Name | Value |
|------|-------|
| cpu  | {env_cpu} |
| os   | {env_os}  |
| compiler | {env_compiler} |
| java runtime version | {env_runtime} |
| java vm mapName | {env_vm_name} |
| java vm version | {env_vm_version} |
| available heap before runnin algorithm | {env_heap} |


## Map details
{map_info}

## {algorithm}

####Basic knowledge
* Theoretical time {al_time}
* Theoretical space {al_space}
<p>
{al_doc}
</p>

#### Test statistics
* Time elapsed: {test_time}
* Space taken max: (doesn't seem to work?) {test_space}
* Steps taken: {test_used_steps} / max steps {test_max_steps}
* weight of path: {test_path_weight}

Intepretation of processed map
* . not visited
* v = visited but not walked
* x = walked path
* S start node
* T target node
* O start and target node if same
* w wall 

```
{test_processed_map}
```