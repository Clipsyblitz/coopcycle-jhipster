import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICoursier } from 'app/shared/model/coursier.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CoursierService } from './coursier.service';
import { CoursierDeleteDialogComponent } from './coursier-delete-dialog.component';

@Component({
  selector: 'jhi-coursier',
  templateUrl: './coursier.component.html',
})
export class CoursierComponent implements OnInit, OnDestroy {
  coursiers: ICoursier[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected coursierService: CoursierService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.coursiers = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.coursierService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<ICoursier[]>) => this.paginateCoursiers(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.coursiers = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCoursiers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICoursier): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCoursiers(): void {
    this.eventSubscriber = this.eventManager.subscribe('coursierListModification', () => this.reset());
  }

  delete(coursier: ICoursier): void {
    const modalRef = this.modalService.open(CoursierDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.coursier = coursier;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCoursiers(data: ICoursier[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.coursiers.push(data[i]);
      }
    }
  }
}
