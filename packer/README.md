### Requirements
- Packer (https://www.packer.io/)
- Ansible (https://www.redhat.com/en/ansible-collaborative)

### Usage
0. Copy variables.pkr.hcl.sample to variables.pkr.hcl and update

1. Install plugins
    ```
    $ packer init builder.pkr.hcl
    ```

2. Build AMI
    ```
    $ packer build -var-file=variables.pkr.hcl builder.pkr.hcl
    ```