<template>
  <div class="common-layout-1">
    <div class="header">
      <router-link :to="{ name: '主页' }" class="logo">
        <span>JMP</span>
      </router-link>
      <ul class="header-nav">
        <router-link tag="li" v-for="auth in authList" :key="auth.id"
          :to="{ name: auth.name }"
          :class="{ current: currentActive(auth.id) }">
          <span>{{ auth.name }}</span>
        </router-link>
      </ul>

      <div class="user-info">
        <el-dropdown>
          <p class="el-dropdown-link login-user" style="cursor: pointer">
            <span>{{ userInfo.nickname }}</span>
          </p>
          <el-dropdown-menu slot="dropdown" class="user-action">
            <el-dropdown-item>
              <div @click="changePassword">
                <i class="fa el-icon-edit"></i>
                修改密码
              </div>
            </el-dropdown-item>
            <el-dropdown-item>
              <div @click="logout">
                <i class="fa el-icon-switch-button"></i>
                退出登录
              </div>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
      <el-dialog title="修改密码" width="700px" :modal-append-to-body="false" :visible.sync="passwordDialog"
        :close-on-click-modal="false">
        <el-form :model="form" :rules="rules" ref="form" label-width="150px" label-position="right">
          <el-form-item label="新密码" prop="new">
            <el-input class="input-380" v-model.trim="form.new" placeholder="请输入" type="password" show-password
              @input="clearChinese($event, 'new')"></el-input>
          </el-form-item>
        </el-form>
        <p class="pwd-ts">注：{{ pwdRegText }}</p>
        <span slot="footer" class="dialog-footer">
          <el-button @click="resetForm">取 消</el-button>
          <el-button type="primary" @click="submit" :loading="loading">确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <el-main class="page-main" :style="{ height: winHeight + 'px' }">
      <router-view></router-view>
    </el-main>
  </div>
</template>
<script>
import { pwdRegText, pwdValidator, chineseReg, blankSpaceReg } from "@/utils/const";

export default {
  data() {
    return {
      passwordDialog: false,
      dialogVisible: true,
      winHeight: window.innerHeight - 66,
      userInfo: { username: "admin", nickname: "管理员" },
      form: {
        new: "",
      },
      rules: {
        new: [
          { required: true, message: "请输入新密码", trigger: "blur" },
          {
            validator: pwdValidator,
            trigger: "blur",
          },
        ],
      },
      loading: false,
      pwdRegText,
      authList: [],
    };
  },
  created() {
    let userInfo = JSON.parse(localStorage.getItem("userInfo"));
    if (userInfo) {
      this.userInfo = userInfo;
    }
    this.initAuth();
  },
  methods: {
    currentActive(id = "") {
      return this.$route.path.split("/")[1] === id;
    },
    initAuth() {
      var flag = false;
      if (this.userInfo.isSuperuser === 1) {
        flag = true;
      }
      let authList = [
        {
          id: "hello",
          name: "你好"
        },
        {
          id: "console",
          name: "后台管理",
          requireAdmin: flag
        }
      ];
      authList.forEach((item) => {
        if (item.requireAdmin === undefined || item.requireAdmin) {
          this.authList.push(item);
        }
      });
    },
    changePassword() {
      this.form.new = "";
      this.passwordDialog = true;
    },
    confirm() {
      this.userInfoDialog = false;
    },
    submit() {
      this.$refs.form.validate(async valid => {
        if (valid) {
          this.loading = true;
          try {
            let url = `/users/${this.userInfo.id}`;
            let param = {
              username: this.userInfo.username,
              nickname: this.userInfo.nickname,
              is_superuser: this.userInfo.is_superuser,
              password: this.form.new,
            };
            this.$http.put(url, param);
            this.$message({
              type: "success",
              message: "密码修改成功",
            });
            this.passwordDialog = false;
          } catch (error) {
            console.log(error.response);
          }
          this.loading = false;
        }
      });
    },
    logout() {
      let oldLoginTime = localStorage.getItem("loginTime");
      localStorage.clear();
      if (oldLoginTime) {
        localStorage.setItem("loginTime", new Date().getTime().toLocaleString());
      }
      this.$router.push("/login");
    },
    resetForm() {
      this.$refs.form.resetFields();
      this.passwordDialog = false;
    },
    clearChinese(val, key) {
      this.form[key] = val.replace(chineseReg, "").replace(blankSpaceReg, "");
    },
    $resize() {
      this.winHeight = window.innerHeight - 66;
    },
  },
  mounted() {
    let _this = this;
    window.addEventListener("resize", _this.$resize, true);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.$resize);
  },
};
</script>
<style>
* {
  box-sizing: border-box;
}

.el-menu-item {
  background-color: #ebf1f6;
}

.el-menu-item .is-active {
  background-color: #188fff !important;
}

.el-submenu [class^="fa"] {
  vertical-align: middle;
  margin-right: 5px;
  width: 24px;
  text-align: center;
  font-size: 16px;
}

.el-step__head.is-process {
  color: #c0c4cc;
  border-color: #c0c4cc;
}

.el-step__title.is-process {
  font-weight: 400;
  color: #c0c4cc;
}

.q-icon {
  margin-right: 10px;
}

.q-crumb {
  padding: 0 0 10px 0;
  display: flex;
  align-items: center;
  border-bottom: 1px solid #ddd;
  margin-bottom: 20px;
}

.q-crumb .q-icon {
  display: block;
  margin-right: 5px;
  color: #33c0fb;
  font-size: 18px;
}

.q-crumb .el-breadcrumb {
  font-size: 16px;
}

.user-action {
  width: 150px;
}

.user-action .el-dropdown-menu__item {
  font-size: 14px;
  height: 40px;
}

.user-action .el-dropdown-menu__item i {
  margin: 0 8px 0 10px;
}

.user-action .el-dropdown-menu--mini .el-dropdown-menu__item {
  line-height: 40px;
}

.top-sidebar {
  margin: 20px auto 0;
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 60px;
  width: 300px;
  border: 1px solid #ccc;
  border-radius: 8px;
}

.top-sidebar li {
  flex: 1;
  list-style: none;
  cursor: pointer;
  border-left: 1px solid #ccc;
  text-align: center;
}

.top-sidebar li :first-child {
  border-left: none;
}

.top-sidebar li span {
  margin-left: 5px;
}

.top-sidebar li .router-link-exact-active {
  color: #317ffe;
}

.header {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  padding: 0 35px 0 50px;
  height: 66px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #00142a;
  z-index: 100;
}

.header .logo {
  text-decoration: none;
}

.header .logo span {
  line-height: 64px;
  color: #fff;
  text-align: center;
  font-size: 20px;
  font-weight: 600;
  text-decoration: none;
  vertical-align: middle;
}

.push-sidebar {
  position: fixed;
  top: 66px;
  left: 0;
  width: 256px;
  text-align: left;
  overflow-y: auto;
  background: #00142a;
  box-shadow: 0 0 10px 0 #ebf1f6;
}

.common-layout-1 {
  margin-top: 66px;
  background-color: rgb(243, 244, 246);
}

.user-info {
  display: flex;
  align-items: center;
}

.user-info .el-dropdown-link {
  outline: none;
}

.login-user {
  display: flex;
  align-items: center;
}

.login-user span {
  display: block;
  color: #fff;
}

.el-main {
  padding: 0 !important;
}

.header-nav {
  position: absolute;
  left: 170px;
  display: flex;
  color: #fff;
  width: 550px;
  height: 100%;
}

.header-nav li {
  height: 100%;
  padding: 0 21px;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  cursor: pointer;
}

.header-nav .current::after {
  content: "";
  position: absolute;
  display: block;
  left: 0;
  bottom: 0;
  height: 5px;
  width: 100%;
  background-color: #1b73ff;
}


.pwd-ts {
  font-size: 12px;
  color: rgba(0, 0, 0, 0.25);
  margin-bottom: 14px;
  margin-left: 64px;
}
</style>
