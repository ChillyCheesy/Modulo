import { ArrayDataSource } from '@angular/cdk/collections';
import { NestedTreeControl } from '@angular/cdk/tree';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { map, mergeMap } from 'rxjs/operators';
import { from, interval, Observable, Subscription } from 'rxjs';
import { ModulesService } from '../../services/modules.service';
import { RedirectService } from '../../services/redirect.service';
import { HTModule } from '../../models/htmodule.model';

interface TreeNode {
  parentName?: string;
  name: string;
  children?: TreeNode[];
}

@Component({
  selector: 'app-modules-tree',
  templateUrl: './modules-tree.component.html',
  styleUrls: ['./modules-tree.component.scss']
})
export class ModulesTreeComponent implements OnInit, OnDestroy {
  treeControl = new NestedTreeControl<TreeNode> (node => node.children);
  modulesTree: TreeNode[] = [];
  dataSource = new ArrayDataSource<TreeNode>(this.modulesTree);
  subscription: Subscription;
    
  hasChild = (_: number, node: TreeNode) => !!node.children && node.children.length > 0;
  
  constructor(private moduleService: ModulesService, private redirectService: RedirectService) { }
  
  ngOnInit(): void {
    this.subscription = this.treeNodeFromHTModules(this.moduleService.getFilteredModules()).subscribe(moduleTree => this.onNewTreeNode(moduleTree));
    this.moduleService.formControl.setValue('');
  }

  private onNewTreeNode(moduleTree: TreeNode): void {
    this.modulesTree.push(moduleTree);
    this.dataSource = new ArrayDataSource<TreeNode>(this.modulesTree);
  }

  private treeNodeFromHTModules(modules: Observable<HTModule[]>): Observable<TreeNode> {
    console.log(modules);
    return modules.pipe(
      mergeMap(modules => {
        this.modulesTree = [];
        return from(modules).pipe(
        mergeMap(module => this.moduleService.getPagesModules(module).pipe(
          map(pages => <TreeNode>{name: module.name, children: pages.map(page => <TreeNode>{parentName: module.name, name: page})})
        ))
      )}),
    )
  }
  
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  redirectToPageInfo(name: string): void {
    this.redirectService.navinavigateToInfoModuleByName(name);
  }

  redirectToPage(moduleName: string, page: string): void {
    if(moduleName) {    
      this.redirectService.navigateToModuleByName(moduleName, page);
    } else {
      this.redirectToPageInfo(page);
    }
  }
}