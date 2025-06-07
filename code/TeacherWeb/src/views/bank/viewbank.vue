<template>
  <el-row class="tac">
      <el-col :span="4">
          <el-menu
          @select="handleSelect"
          default-active="0"
           class="el-menu-vertical-demo" 
          >   
            <el-menu-item 
                v-for="bank in banks" 
                :key="bank.bank_id" 
                :index="bank.bank_id.toString()">
                <el-icon><document /></el-icon>
                <span>{{ bank.bank_name }}</span>
            </el-menu-item>
          </el-menu>
      </el-col>
      
      <el-col :span="20" >
          <el-table 
            
            :data="filterTableData" style="width: 100%">
              <el-table-column label="题库序号" prop="bank_id" width="100"/>
              <el-table-column label="序号" prop="question_id" width="100"/>
              <el-table-column label="题目名称" prop="quName" />
              <el-table-column label="分值" prop="scorevalue" width="100"/>
              <el-table-column label="题型" prop="quType" width="100"/>
              <el-table-column align="right">
                  <template #header>
                    <div style="display: flex; align-items: center; gap: 10px;">
                      <el-input 
                        v-model="search" 
                        size="small" 
                        placeholder="题目名称检索" 
                        style="flex-grow: 1; width: 100%;" />
                      <el-button size="small" type="primary" 
                        @click="showAddDialog">
                        增加题目
                      </el-button>
                    </div>
                  </template>

                  <template #default="scope">
                    <el-button 
                        size="small" 
                        @click="handleEdit( scope.row)">
                        查看/修改
                    </el-button>

                    <el-button
                        size="small"
                        type="danger"
                        @click="handleDelete(scope.$index, scope.row)">
                        删除
                    </el-button>
                  </template>
              </el-table-column>
          </el-table>
      </el-col>
  </el-row>

  <!-- 查看/修改题目 -->
  <el-dialog
  title="查看/修改题目"
  v-model:model-value="checkDialogVisible"
  width="30%"
  @close="resetForm"
  >
    <el-form :model="currentQuestion">
      <el-form-item label="题目类型">
        <el-tag>{{ currentQuestion.quType }}</el-tag>
      </el-form-item>
      <el-form-item label="题目名称">
        <el-input v-model="currentQuestion.quName" placeholder="请输入题目名称" />
      </el-form-item>
      <el-form-item label="题目内容">
        <el-input
          type="textarea"
          v-model="currentQuestion.content"
          placeholder="请输入题目内容"
        />
      </el-form-item>
      <div v-if="currentQuestion.quType === '单选题'||currentQuestion.quType === '多选题'">
        <el-form-item v-for="( option,index) in options" :key="index" :label="'选项 ' + (index + 1)">
          <el-input
            v-model="options[index]"
            placeholder="请输入选项"
          />
        </el-form-item>
      </div>

      <el-form-item label="分值">
        <el-input-number v-model="currentQuestion.scorevalue" :controls-position="'right'" />
      </el-form-item>
      <el-form-item label="答案">
        <el-input v-model="currentQuestion.quAnswer" placeholder="请输入答案" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="checkDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="saveQuestion">保存</el-button>
    </template>
  </el-dialog>

  <!-- 添加题目 -->
  <el-dialog
    title="添加题目"
    v-model:model-value="addDialogVisible"
    width="30%"
  >
    <el-form :model="newQuestion">
      <el-form-item label="所属题库">
        <el-select v-model="newQuestion.bank_id" placeholder="请选择题库">
          <el-option
            v-for="bank in banks"
            :key="bank.bank_id"
            :label="bank.bank_name"
            :value="bank.bank_id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="题目类型">
        <el-select v-model="newQuestion.quType" placeholder="请选择题目类型">
          <el-option label="单选题" value="单选题"></el-option>
          <!-- <el-option label="多选题" value="多选题"></el-option>
          <el-option label="填空题" value="填空题"></el-option>
          <el-option label="判断题" value="判断题"></el-option>
          <el-option label="简答题" value="简答题"></el-option> -->
        </el-select>
      </el-form-item>
      <el-form-item label="题目名称">
        <el-input v-model="newQuestion.quName" placeholder="请输入题目名称" />
      </el-form-item>
      <el-form-item label="题目内容">
        <el-input
          type="textarea"
          v-model="newQuestion.content"
          placeholder="请输入题目内容"
        />
      </el-form-item>

      <!-- 根据题目类型动态显示选项 -->
      <div v-if="newQuestion.quType === '单选题' || newQuestion.quType === '多选题'">
        <el-form-item v-for="(option, index) in newQuestion.quOptions" :key="index" :label="'选项 ' + (index + 1)">
          <el-input
            v-model="newQuestion.quOptions[index]"
            placeholder="请输入选项"
          />
        </el-form-item>
      </div>

      <el-form-item label="分值">
        <el-input-number v-model="newQuestion.scorevalue" />
      </el-form-item>
      <el-form-item label="答案">
        <el-input v-model="newQuestion.quAnswer" placeholder="请输入答案" />
      </el-form-item>
      
    </el-form>
    <template #footer>
      <el-button @click="addDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="addQuestion">保存</el-button>
    </template>
  </el-dialog>

</template>

<script lang="ts" setup >
import { computed, ref ,watch ,onMounted,onBeforeUnmount} from 'vue'
import { getBanks, getQuestionsByBankId,createQuestion ,updateQuestion,deleteQuestion} from '../../api/bank'
import { useUserStore } from "../../store/user";
import { Document,} from '@element-plus/icons-vue';
import { ElMessageBox } from 'element-plus';
import { ElDialog } from 'element-plus';

const banks =ref<Bank[]>([]);
const tableData = ref<Question[]>([]);

interface Bank {
  bank_id : number
  bank_name : string
}

interface Question {
  bank_id : number
  question_id: number
  quName: string
  content: string
  quOptions :string[]
  quAnswer:string
  scorevalue: number
  quType: string
}
interface CreateQuestionData {
  quName: string;
  content: string;
  quOptions: string[];
  quAnswer: string;
  scorevalue: number;
  quType: string;
}


const userStore = useUserStore();
const userId = computed(() => userStore.user.user_id);

// 设置一个标志位，用于跟踪组件是否挂载
let isMounted = ref(false);
onMounted(async () => {
  isMounted.value = true;
  await fetchBanks();
  await fetchQuestions();
});
onBeforeUnmount(() => {
isMounted.value= false;
});
watch(isMounted, async (newVal) => {
if (newVal) {
  await fetchBanks();
  await fetchQuestions();
}
});
// 获取题库列表
const fetchBanks = async () => {
try {
  const bankdata = await getBanks(userId.value);
  banks.value = bankdata.bank_list;
} catch (error) {
  console.error('获取题库列表失败', error);
}
};
// 获取题目列表
const fetchQuestions = async () => {
try {
  let allQuestions : Question[] = [];
  for (let bank of banks.value) {
    const questionData = await getQuestionsByBankId(bank.bank_id);
    allQuestions = allQuestions.concat(questionData.questions_list);
  }
  tableData.value = allQuestions;
} catch (error) {
  console.error('获取题目列表失败', error);
}
};

//搜索题目
const search = ref('')
const selectedBankId = ref<number | null>(null);
const filterTableData = computed(() => {
if (selectedBankId.value === null) {
  return tableData.value.filter(
    (data) => !search.value || data.quName.toLowerCase().includes(search.value.toLowerCase())
  );
} else {
  return tableData.value.filter(
    (data) => data.bank_id === selectedBankId.value && 
    (!search.value || data.quName.toLowerCase().includes(search.value.toLowerCase()))
  );
}
});

const handleSelect = (key: string) => {
selectedBankId.value = parseInt(key, 10);
fetchQuestions();
};


//增添题目
const addDialogVisible = ref(false);
const newQuestion = ref<Question>({} as Question);
const showAddDialog = () => {
addDialogVisible.value = true;
};
const addQuestion =async () => {
try {
  
  const newQuestionData: CreateQuestionData = {
    quName: newQuestion.value.quName,
    content: newQuestion.value.content,
    quOptions: newQuestion.value.quOptions,
    quAnswer: newQuestion.value.quAnswer,
    scorevalue: newQuestion.value.scorevalue,
    quType: newQuestion.value.quType,
  };
  const bankId = newQuestion.value.bank_id;
  const response = await createQuestion(bankId, newQuestionData);
  if (response) {
    // 更新视图或调用其他逻辑
    console.log('题目创建成功:',newQuestion.value);
    addDialogVisible.value = false;
    newQuestion.value = {} as Question; // 重置表单
    fetchQuestions(); // 重新获取题目列表
  } else {
    console.error('题目创建失败', response.message);
  }
} catch (error) {
  console.error('创建题目时发生错误', error);
}
};
watch(() => newQuestion.value.quType, (newVal) => {
if (newVal === '单选题' || newVal === '多选题') {
  // 初始化选项数组，可以根据需要设置默认选项数量
  newQuestion.value.quOptions = ['','','',''];
} else {
  newQuestion.value.quOptions = [];
}
}, { immediate: true });


//查看/修改题目
const checkDialogVisible = ref(false);
const currentQuestion = ref<Question>({} as Question);
const options = ref<string[]>(['', '', '', '']); // 默认四个空选项

const handleEdit = ( row: Question) => {
currentQuestion.value = { ...row };

if (row.quType === '单选题'||row.quType === '多选题') {
  options.value = row.quOptions;
} else {
  options.value = [];
}
checkDialogVisible.value = true;

};
const saveQuestion =  async ()=> {
// 查找当前题目在tableData中的索引
const index = tableData.value.findIndex(q => q.question_id === currentQuestion.value.question_id);

// 如果找到了题目，更新它
if (index !== -1) {
  const bankId = currentQuestion.value.bank_id;
  const questionId = currentQuestion.value.question_id;
  const questionData: CreateQuestionData = {
    quName: currentQuestion.value.quName,
    content: currentQuestion.value.content,
    quOptions: options.value, // 确保这里绑定的是正确的选项数组
    quAnswer: currentQuestion.value.quAnswer,
    scorevalue: currentQuestion.value.scorevalue,
    quType: currentQuestion.value.quType,
  };
  try {
  const response = await updateQuestion(bankId, questionId, questionData);
  if (response) {
    if (response.success) {
      console.log('题目更新成功');
      tableData.value[index] = { ...currentQuestion.value };
      checkDialogVisible.value = false;
      currentQuestion.value = {} as Question;
    } else {
      console.error('题目更新失败:', response.message);
    }
  } else {
    console.error('题目更新失败，未收到有效的响应');
  }
} catch (error) {
  console.error('更新题目时发生错误');
  // if (error.response && error.response.data && error.response.data.message) {
  //   console.error('更新题目时发生错误:', error.response.data.message);
  // } else {
  //   console.error('更新题目时发生错误，且无法获取错误详情');
  // }
}
} else {
  console.error('题目未找到，无法保存');
}

// 在这里更新 tableData 或者调用 API
checkDialogVisible.value = false;
};
const resetForm = () => {
currentQuestion.value = {} as Question;
options.value = []; // 重置选项数组
};
watch(currentQuestion, (newVal) => {
if (newVal.quType === '单选题'||newVal.quType === '多选题') {
  options.value = newVal.quOptions;
} else {
  options.value = [];
}
}, { deep: true, immediate: true });


//删除题目
const handleDelete = (index: number, row: Question) => {
const bankId = row.bank_id;
const questionId = row.question_id;
ElMessageBox.confirm(
  '此操作将删除该题目, 是否继续?',
  '警告',
  {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }
)
.then(async () => {
  try {
    const response = await deleteQuestion(bankId, questionId);
    if (response) {
      console.log('题目删除成功');
      tableData.value.splice(index, 1); // 从列表中移除题目
    } else {
      console.error('题目删除失败', response.message);
    }
  } catch (error) {
    console.error('删除题目时发生错误', error);
  }
})
.catch(() => {
  console.log('删除操作已取消');
});
}


</script>


<style scoped>


</style>