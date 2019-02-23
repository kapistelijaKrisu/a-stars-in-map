# Analysis on {algorithm}

## Environment specs

| Name | Value |
|------|-------|
| cpu  | {env_cpu} |
| os   | {env_os}  |
| compiler | {env_compiler} |
| java runtime version | {env_runtime} |
| java vm mapName | {env_vm_name} |
| java vm version | {env_vm_version} |
| available heap before running algorithm | {env_heap} |


## Map details
{map_info}

## {algorithm}

#### Basic knowledge
* Theoretical time {al_time}
* Theoretical space {al_space}

#### Algorithm implementation information
{al_doc}

#### Test statistics
* Time elapsed: {test_time}
* Space taken max: {test_space} (due to how JVM works might not be accurate)
* Steps taken: {test_used_steps} / max steps {test_max_steps}
* weight of path: {test_path_weight}

Interpretation of processed map
* . not visited
* ! = visited but not walked
* X = walked path
* S start node
* T target node
* O start and target node if same
* @ wall 

```
{test_processed_map}
```