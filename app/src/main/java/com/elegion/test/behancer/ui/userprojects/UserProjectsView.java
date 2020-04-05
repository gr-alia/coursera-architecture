package com.elegion.test.behancer.ui.userprojects;

import com.elegion.test.behancer.common.BaseView;
import com.elegion.test.behancer.data.model.project.Project;

import java.util.List;

public interface UserProjectsView extends BaseView {

    void showUserProjects(List<Project> projects);
}
