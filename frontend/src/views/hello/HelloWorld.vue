<template>
  <div class="hello">
    <WrapComponent>
      <el-form slot="form" :inline="true" class="search-form">
        <el-form-item label="" prop="keyword">
          <el-input style="width: 300px;" v-model="order" placeholder="输入框"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="oneMethod" type="primary" icon="el-icon-refresh">按钮</el-button>
        </el-form-item>
      </el-form>
    </WrapComponent>
  </div>
</template>

<script>
import WrapComponent from "@/components/WrapComponent";
import { filterNullValue } from "@/utils/commonMethods";
export default {
  name: 'HelloWorld',
  components: {
    WrapComponent
  },
  data() {
    return {
      order: ""
    }
  },
  methods: {
    oneMethod() {
      let params = {
        order: this.order,
      };
      this.$http
        .get("/oneMethod", { params: filterNullValue(params) })
        .then(() => {
        this.$notifyMessage(`操作成功`, { type: "success" });
      });
    }
  }
}
</script>

