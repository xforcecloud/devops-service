import { useLocalStore } from 'mobx-react-lite';
import { axios } from '@choerodon/boot';
import { handlePromptError } from '../../../../../../../utils';

export default function useStore() {
  return useLocalStore(() => ({
    canCreate: false,
    get getCanCreate() {
      return this.canCreate;
    },
    setCanCreate(flag) {
      this.canCreate = flag;
    },

    permissionUpdate({ projectId, clusterId, ...rest }) {
      const data = {
        clusterId,
        ...rest,
      };
      return axios.post(`/devops/v1/projects/${projectId}/clusters/${clusterId}/permission`, JSON.stringify(data));
    },

    async checkCreate(projectId) {
      try {
        const res = await axios.get(`devops/v1/projects/${projectId}/clusters/check_enable_create`);
        this.setCanCreate(handlePromptError(res));
      } catch (e) {
        this.setCanCreate(false);
      }
    },
  }));
}
